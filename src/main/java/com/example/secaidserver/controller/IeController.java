package com.example.secaidserver.controller;

import com.example.secaidserver.constant.MessageConstants;
import com.example.secaidserver.model.ResponseMessage;
import com.example.secaidserver.model.file.SessionFile;
import com.example.secaidserver.model.questionnaire.QuestSection;
import com.example.secaidserver.service.IeService;
import com.example.secaidserver.service.UploadFileService;
import com.example.secaidserver.worker.ClassJsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Controller for import/export operations.
 */
@Controller
@CrossOrigin("http://localhost:4200")
@RequestMapping("/ie")
public class IeController {

    private final UploadFileService uploadFileService;

    private final IeService ieService;

    @Autowired
    public IeController(UploadFileService uploadFileService, IeService ieService) {
        this.uploadFileService = uploadFileService;
        this.ieService = ieService;
    }

    @GetMapping("/archive/program")
    public ResponseEntity<byte[]> exportProgram() throws IOException {
        List<SessionFile> files = uploadFileService.getAllFiles();
        String zipName = "program.zip";
        ByteArrayOutputStream baos = ieService.getZipFromListOfFiles(files);

        return ResponseEntity.ok()
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipName + "\"")
                             .body(baos.toByteArray());
    }

    @GetMapping("/export/program")
    public ResponseEntity<byte[]> exportProgramAsJson() {
        JSONObject exportJson = ieService.buildProgramJson();
        byte[] exportContent = exportJson.toString().getBytes();

        return ResponseEntity.ok()
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"program.json\"")
                             .body(exportContent);
    }

    @PostMapping("/import/program")
    public ResponseEntity<ResponseMessage> importProgramFromJson(@RequestParam("file0") MultipartFile importJsonFile) throws IOException {
        JSONObject programJsonObj = new JSONObject(fileJsonToJsonTokener(importJsonFile));
        ieService.importProgramJson(programJsonObj);

        String message = MessageConstants.PROGRAM_SUCCESS_IMPORT;
        return ResponseEntity.ok().body(new ResponseMessage(message));
    }

    @PutMapping("/export/questionnaire")
    public ResponseEntity<byte[]> exportQuestionnaireAsJson(@RequestBody final List<QuestSection> questionnaire) {
        JSONArray exportJson = new JSONArray(questionnaire);
        byte[] exportContent = exportJson.toString().getBytes();

        return ResponseEntity.ok()
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"questionnaire.json\"")
                             .body(exportContent);
    }

    @PostMapping("/import/questionnaire")
    public ResponseEntity<List<QuestSection>> importQuestionnaireFromJson(@RequestParam("file0") MultipartFile importJsonFile) throws IOException {
        JSONArray questJsonObj = new JSONArray(fileJsonToJsonTokener(importJsonFile));
        List<QuestSection> questionnaire = ClassJsonParser.parseQuestionnaire(questJsonObj);

        return ResponseEntity.ok().body(questionnaire);
    }

    private JSONTokener fileJsonToJsonTokener(final MultipartFile importJsonFile) throws JSONException, IOException {
        return new JSONTokener(new InputStreamReader(importJsonFile.getInputStream(), StandardCharsets.UTF_8));
    }

}
