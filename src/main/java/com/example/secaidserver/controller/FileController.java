package com.example.secaidserver.controller;

import com.example.secaidserver.constant.MessageConstants;
import com.example.secaidserver.model.file.MultipleFilesRequest;
import com.example.secaidserver.model.abstractive.FileDB;
import com.example.secaidserver.model.file.ResponseFile;
import com.example.secaidserver.model.ResponseMessage;
import com.example.secaidserver.model.enums.TemplateFileEnum;
import com.example.secaidserver.model.enums.UploadFileEnum;
import com.example.secaidserver.service.TemplateFileService;
import com.example.secaidserver.service.UploadFileService;
import com.example.secaidserver.service.implementation.UploadFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin("http://localhost:4200")
@Validated
@RequestMapping("/files")
public class FileController {

    private final UploadFileService uploadFileService;

    private final TemplateFileService templateFileService;

    @Autowired
    public FileController(UploadFileServiceImpl fileService, TemplateFileService templateFileService) {
        this.uploadFileService = fileService;
        this.templateFileService = templateFileService;
    }

    private static ResponseFile FileDbToResponseFile(FileDB file) {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/one/")
                .path(file.getId())
                .toUriString();

        return new ResponseFile(file.getId(), file.getName(), fileDownloadUri, file.getType(), file.getData().length);
    }

    @GetMapping("")
    public ResponseEntity<List<ResponseFile>> downloadAllFiles() {
        List<ResponseFile> files = uploadFileService.getAllFiles().stream()
                .map(FileController::FileDbToResponseFile).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/{type}")
    public ResponseEntity<byte[]> downloadFileByType(@PathVariable @Pattern(regexp = "^((?!IMPLEMENTATION_DOC).)*$",
            message = "Type should be different then: IMPLEMENTATION_DOC") String type) {
        FileDB fileDB = uploadFileService.getFile(UploadFileEnum.valueOf(type));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<byte[]> downloadFileById(@PathVariable String id) {
        FileDB fileDB = uploadFileService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    @GetMapping("/many/{type}")
    public ResponseEntity<List<ResponseFile>> downloadFilesByType(@PathVariable String type) {
        List<ResponseFile> files = uploadFileService.getAllFilesByType(UploadFileEnum.valueOf(type)).stream()
                .map(FileController::FileDbToResponseFile).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/template/{type}")
    public ResponseEntity<Resource> downloadTemplate(@PathVariable String type) {
        Resource file = templateFileService.loadFile(TemplateFileEnum.valueOf(type));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }


    @PostMapping("/upload/{type}")
    public ResponseEntity<ResponseMessage> uploadFile(@PathVariable String type,
                                                      @RequestParam("file0") MultipartFile file) {
        String message;
        try {
            UploadFileEnum fileType = UploadFileEnum.valueOf(type);

            uploadFileService.deleteAllByType(fileType);
            uploadFileService.upload(file, fileType);
            message = MessageConstants.SUCCESS_UPLOAD + file.getOriginalFilename();

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (IOException e) {
            message = MessageConstants.FAILED_UPLOAD + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PostMapping("/upload-many")
    public ResponseEntity<ResponseMessage> uploadFiles(@ModelAttribute() MultipleFilesRequest multipleFilesRequest) {
        String message;
        try {
            List<String> fileNames = new ArrayList<>();

            uploadFileService.deleteAllByType(UploadFileEnum.IMPLEMENTATION_DOC);
            for (MultipartFile file : multipleFilesRequest.getFiles()) {
                if (file != null) {
                    uploadFileService.upload(file, UploadFileEnum.IMPLEMENTATION_DOC);
                    fileNames.add(file.getOriginalFilename());
                }
            }

            message = MessageConstants.SUCCESS_UPLOADS + fileNames;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (IOException e) {
            message = MessageConstants.FAILED_UPLOADS;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/last-step")
    public ResponseEntity<Integer> getLastProgramStep() {
        return ResponseEntity.ok(uploadFileService.getLastProgramStep());
    }

    @DeleteMapping("/reset")
    public ResponseEntity<ResponseMessage> resetSessionDb() {
        uploadFileService.deleteAll();

        return ResponseEntity.ok(new ResponseMessage(MessageConstants.SUCCESS_SESSION_DB_RESET));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteFile(@PathVariable String id) {
        uploadFileService.deleteById(id);

        return ResponseEntity.ok(new ResponseMessage(MessageConstants.SUCCESS_DELETE));
    }

    // "Error Handling for REST with Spring" on Baeldung for creating a global handler
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ResponseMessage> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.badRequest()
                .body(new ResponseMessage("Not valid due to validation error: " + e.getMessage()));
    }

}
