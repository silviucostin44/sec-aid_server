package com.example.secaidserver.unitary.controller;

import com.example.secaidserver.config.JwtAuthenticationEntryPoint;
import com.example.secaidserver.controller.AuthenticationController;
import com.example.secaidserver.service.implementation.AuthenticationServiceImpl;
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
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerUnitTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthenticationController authenticationController;

    @MockBean
    AuthenticationServiceImpl authenticationService;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Test
    public void createAuthenticationTokenTest() {
        // todo:
    }

    @Test
    public void registerUserTest() {
        // todo:
    }
}
