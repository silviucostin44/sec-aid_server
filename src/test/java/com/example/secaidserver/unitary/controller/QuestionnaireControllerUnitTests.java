package com.example.secaidserver.unitary.controller;

import com.example.secaidserver.config.JwtAuthenticationEntryPoint;
import com.example.secaidserver.controller.QuestionnaireController;
import com.example.secaidserver.service.implementation.QuestionnaireServiceImpl;
import com.example.secaidserver.service.implementation.UserDetailsServiceImpl;
import com.example.secaidserver.worker.JwtTokenUtil;
import com.example.secaidserver.worker.QuestionnaireWorker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(QuestionnaireController.class)
public class QuestionnaireControllerUnitTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    QuestionnaireController questionnaireController;

    @MockBean
    QuestionnaireServiceImpl questionnaireService;

    @MockBean
    QuestionnaireWorker questionnaireWorker;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Test
    public void testGetDefaultQuestionnaire() {
        // todo:
    }

    @Test
    public void testUpdateQuestRatings() {
        // todo:
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    public void testSaveQuestionnaire() {
        // todo:
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    public void testGetQuestionnaire() {
        // todo:
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    public void testGetAllQuestionnaires() {
        // todo:
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    public void testManageQuestionnaires() {
        // todo:
    }
}
