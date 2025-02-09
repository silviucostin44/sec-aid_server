package com.example.secaidserver.unitary.controller;

import com.example.secaidserver.config.JwtAuthenticationEntryPoint;
import com.example.secaidserver.controller.FileController;
import com.example.secaidserver.service.implementation.TemplateFileServiceImpl;
import com.example.secaidserver.service.implementation.UploadFileServiceImpl;
import com.example.secaidserver.service.implementation.UserDetailsServiceImpl;
import com.example.secaidserver.worker.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FileController.class)
public class FileControllerUnitTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FileController fileController;

    @MockBean
    UploadFileServiceImpl uploadFileService;

    @MockBean
    TemplateFileServiceImpl templateFileService;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Test
    public void downloadAllFilesTest() {
        // todo:
    }

    @Test
    public void downloadFileByTypeTest() {
        // todo:
    }

    @Test
    public void downloadFileByIdTest() {
        // todo:
    }

    @Test
    public void downloadFilesByTypeTest() {
        // todo:
    }

    @Test
    public void downloadTemplateTest() {
        // todo:
    }

    @Test
    public void uploadFileTest() {
        // todo:
    }

    @Test
    public void uploadFilesTest() {
        // todo:
    }

    @Test
    public void getLastProgramStepTest() {
        // todo:
    }

    @Test
    public void resetSessionDbTest() {
        // todo:
    }

    @Test
    public void deleteFileTest() {
        // todo:
    }
}
