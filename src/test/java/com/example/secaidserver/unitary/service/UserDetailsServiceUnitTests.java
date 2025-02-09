package com.example.secaidserver.unitary.service;

import com.example.secaidserver.repository.UserRepository;
import com.example.secaidserver.service.implementation.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceUnitTests {
    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Mock
    UserRepository userRepository;

    @Test
    public void loadUserByUsernameTest() {
        // todo:
    }
}
