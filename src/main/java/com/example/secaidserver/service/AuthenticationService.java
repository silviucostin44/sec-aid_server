package com.example.secaidserver.service;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    /**
     * Creates an token for an authenticated user.
     *
     * @param username the username.
     * @param password the password.
     * @return the token.
     */
    String createAuthenticationToken(final String username, final String password) throws AuthenticationException;

    /**
     * Registers a new account for a new user.
     *
     * @param username the username.
     * @param password the password.
     */
    void addUser(final String username, final String password) throws IllegalArgumentException;
}
