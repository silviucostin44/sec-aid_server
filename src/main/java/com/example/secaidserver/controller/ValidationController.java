package com.example.secaidserver.controller;

import com.example.secaidserver.model.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@Controller
@Validated
public class ValidationController {

    // todo v3: implementation hint on FileController, last method
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ResponseMessage> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.badRequest()
                             .body(new ResponseMessage("not valid due to validation error: " + e.getMessage()));
    }
}
