package com.example.redditclone.controller;

import com.example.redditclone.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ErrorResponse> mailError(){
        ErrorResponse errorResponse = new ErrorResponse("An error occurred", Instant.now(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

}
