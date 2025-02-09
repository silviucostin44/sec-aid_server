package com.example.secaidserver.unitary.service;

import com.example.secaidserver.repository.RoleRepository;
import com.example.secaidserver.repository.UserRepository;
import com.example.secaidserver.service.implementation.AuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceUnitTests {
    @InjectMocks
    AuthenticationServiceImpl authenticationService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Test
    public void createAuthenticationTokenTest() {
        // todo
    }

    @Test
    public void addUserTest() {
        // todo
    }
}
