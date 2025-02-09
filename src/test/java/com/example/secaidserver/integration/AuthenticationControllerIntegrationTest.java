package com.example.secaidserver.integration;

import com.example.secaidserver.constant.MessageConstants;
import com.example.secaidserver.controller.AuthenticationController;
import com.example.secaidserver.model.ResponseMessage;
import com.example.secaidserver.model.security.JwtRequest;
import com.example.secaidserver.model.security.JwtResponse;
import com.example.secaidserver.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthenticationControllerIntegrationTest {
    int existingUsers;

    @Autowired
    AuthenticationController authenticationService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void collectPretestingInfo() {
        existingUsers = userRepository.findAll().size();
    }

    @Test
    @Transactional
    public void registerTest() {
        JwtRequest registerRequest = new JwtRequest();
        registerRequest.setUsername("testUsername");
        registerRequest.setPassword("testPass");
        ResponseMessage responseMessage = authenticationService.registerUser(registerRequest).getBody();

        assertNotNull(responseMessage);
        assertEquals(MessageConstants.SUCCESS_USER_ADDED, responseMessage.getMessage());

        responseMessage = authenticationService.registerUser(registerRequest).getBody();

        assertNotNull(responseMessage);
        assertEquals(MessageConstants.BAD_CREDENTIALS, responseMessage.getMessage());

        deleteCreatedUser(registerRequest);
    }

    @Test
    @Transactional
    public void authenticateTest() {
        JwtRequest registerRequest = new JwtRequest();
        registerRequest.setUsername("testUsername");
        registerRequest.setPassword(new BCryptPasswordEncoder().encode("testPass"));

        ResponseEntity<JwtResponse> jwtResponse = authenticationService.createAuthenticationToken(registerRequest);

        assertEquals(400, jwtResponse.getStatusCodeValue());
        assertEquals(MessageConstants.BAD_CREDENTIALS, Objects.requireNonNull(jwtResponse.getBody()).getJwtToken());

        ResponseMessage responseMessage = authenticationService.registerUser(registerRequest).getBody();

        assertNotNull(responseMessage);
        assertEquals(MessageConstants.SUCCESS_USER_ADDED, responseMessage.getMessage());

        registerRequest.setPassword("testPass");
        jwtResponse = authenticationService.createAuthenticationToken(registerRequest);

        assertNotNull(jwtResponse);
        assertEquals(200, jwtResponse.getStatusCodeValue());
        assertTrue(Objects.requireNonNull(jwtResponse.getBody()).getJwtToken().length() > 0);

        deleteCreatedUser(registerRequest);
    }

    void deleteCreatedUser(JwtRequest registerRequest) {
        userRepository.deleteByUsername(registerRequest.getUsername());
    }

    @AfterEach
    void testAfterTestingDeletion() {
        Assertions.assertEquals(userRepository.findAll().size(), existingUsers);
    }

}
