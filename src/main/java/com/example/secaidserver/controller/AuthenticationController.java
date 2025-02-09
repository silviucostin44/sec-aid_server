package com.example.secaidserver.controller;

import com.example.secaidserver.constant.MessageConstants;
import com.example.secaidserver.model.ResponseMessage;
import com.example.secaidserver.model.security.JwtRequest;
import com.example.secaidserver.model.security.JwtResponse;
import com.example.secaidserver.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Manages authentication using Jwt token.
 */
@RestController
@CrossOrigin("http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationService authService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authService = authenticationService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody final JwtRequest authRequest) {
        try {
            final String token = authService.createAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (BadCredentialsException bce) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JwtResponse(MessageConstants.BAD_CREDENTIALS));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> registerUser(@RequestBody final JwtRequest registerRequest) {
        try {
            authService.addUser(registerRequest.getUsername(), registerRequest.getPassword());

            return ResponseEntity.ok(new ResponseMessage(MessageConstants.SUCCESS_USER_ADDED));
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(MessageConstants.EXISTING_USER));
        }
    }
}
