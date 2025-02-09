package com.example.secaidserver.unitary.controller;

import com.example.secaidserver.config.JwtAuthenticationEntryPoint;
import com.example.secaidserver.controller.IeController;
import com.example.secaidserver.service.implementation.IeServiceImpl;
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
@WebMvcTest(IeController.class)
public class IeControllerUnitTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    IeController ieController;

    @MockBean
    IeServiceImpl ieService;

    @MockBean
    UploadFileServiceImpl uploadFileService;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Test
    public void exportProgramTest() {
        // todo:
    }

    @Test
    public void exportProgramAsJsonTest() {
        // todo:
    }

    @Test
    public void importProgramFromJsonTest() {
        // todo:
    }

    @Test
    public void importQuestionnaireFromJsonTest() {
        // todo:
    }
}
