package com.example.secaidserver.integration;

import com.example.secaidserver.constant.MessageConstants;
import com.example.secaidserver.controller.FileController;
import com.example.secaidserver.model.ResponseMessage;
import com.example.secaidserver.model.enums.TemplateFileEnum;
import com.example.secaidserver.model.enums.UploadFileEnum;
import com.example.secaidserver.model.file.MultipleFilesRequest;
import com.example.secaidserver.model.file.ResponseFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FileControllerIntegrationTests {

    private final MockMultipartFile mockFile = new MockMultipartFile("file",
                                                                     "hello.txt",
                                                                     MediaType.TEXT_PLAIN_VALUE,
                                                                     "Hello, World!".getBytes());
    @Autowired
    private FileController fileController;

    @Test
    public void uploadFileTest() {
        ResponseMessage responseMessage = fileController.uploadFile(UploadFileEnum.ASSETS_INVENTORY.toString(), mockFile).getBody();

        assert responseMessage != null;
        assertEquals(responseMessage.getMessage(), MessageConstants.SUCCESS_UPLOAD + mockFile.getOriginalFilename());

        fileController.resetSessionDb();
    }

    @Test
    public void uploadManyFileTest() {
        MultipleFilesRequest multipleFilesRequest = new MultipleFilesRequest();
        multipleFilesRequest.setFile0(mockFile);
        multipleFilesRequest.setFile1(mockFile);
        ResponseMessage responseMessage = fileController.uploadFiles(multipleFilesRequest).getBody();

        assert responseMessage != null;
        List<String> fileNames = new ArrayList<>();
        fileNames.add(mockFile.getOriginalFilename());
        fileNames.add(mockFile.getOriginalFilename());
        assertEquals(responseMessage.getMessage(), MessageConstants.SUCCESS_UPLOADS + fileNames);
        assertEquals(Objects.requireNonNull(fileController.downloadAllFiles().getBody()).size(), 2);

        fileController.resetSessionDb();
    }

    @Test
    public void downloadFileByTypeTest() {
        fileController.uploadFile(UploadFileEnum.ASSETS_INVENTORY.toString(), mockFile).getBody();

        byte[] bao = fileController.downloadFileByType(UploadFileEnum.ASSETS_INVENTORY.toString()).getBody();
        assertNotNull(bao);
        assertTrue(bao.length > 0);

        fileController.resetSessionDb();
    }

    @Test
    public void downloadFileByIdTest() {
        fileController.uploadFile(UploadFileEnum.ASSETS_INVENTORY.toString(), mockFile).getBody();
        List<ResponseFile> files = fileController.downloadAllFiles().getBody();

        assertNotNull(files);
        assertEquals(1, files.size());

        byte[] bao = fileController.downloadFileById(files.get(0).getId()).getBody();
        assertNotNull(bao);
        assertTrue(bao.length > 0);

        fileController.resetSessionDb();
    }

    @Test
    public void downloadFilesByTypeTest() {
        fileController.uploadFile(UploadFileEnum.ASSETS_INVENTORY.toString(), mockFile).getBody();

        List<ResponseFile> files = fileController.downloadFilesByType(UploadFileEnum.ASSETS_INVENTORY.toString()).getBody();

        assertNotNull(files);
        assertEquals(1, files.size());
        assertEquals(mockFile.getOriginalFilename(), files.get(0).getName());
        assertEquals(mockFile.getSize(), files.get(0).getSize());

        fileController.resetSessionDb();
    }

    @Test
    public void downloadTemplate() throws IOException {
        Resource template = fileController.downloadTemplate(TemplateFileEnum.ASSETS_INVENTORY.toString()).getBody();

        assertNotNull(template);
        assertTrue(template.isFile());
        assertTrue(template.contentLength() > 0);
    }

    @Test
    public void lastStepTest() {
        assertEquals(1, fileController.getLastProgramStep().getBody());

        fileController.uploadFile(UploadFileEnum.CURRENT_PROFILE.toString(), mockFile).getBody();

        assertEquals(UploadFileEnum.CURRENT_PROFILE.getStep(), fileController.getLastProgramStep().getBody());

        fileController.resetSessionDb();
    }

    @Test
    public void deleteFileTest() {
        fileController.uploadFile(UploadFileEnum.ASSETS_INVENTORY.toString(), mockFile).getBody();
        List<ResponseFile> files = fileController.downloadAllFiles().getBody();

        assertNotNull(files);
        assertEquals(1, files.size());

        ResponseMessage responseMessage = fileController.deleteFile(files.get(0).getId()).getBody();
        assertNotNull(responseMessage);
        assertEquals(MessageConstants.SUCCESS_DELETE, responseMessage.getMessage());

        fileController.resetSessionDb();
    }

    @AfterEach
    void testAfterTestingDeletion() {
        Assertions.assertEquals(Objects.requireNonNull(fileController.downloadAllFiles().getBody()).size(), 0);
    }
}
