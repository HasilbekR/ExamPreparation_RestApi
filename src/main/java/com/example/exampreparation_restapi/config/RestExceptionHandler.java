package com.example.exampreparation_restapi.config;

import com.example.exampreparation_restapi.exception.AuthenticationFailedException;
import com.example.exampreparation_restapi.exception.DataNotFoundException;
import com.example.exampreparation_restapi.exception.RequestValidationException;
import com.example.exampreparation_restapi.exception.UniqueObjectException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {AuthenticationFailedException.class})
    public ResponseEntity<String> authenticationFailedExceptionHandler(
            AuthenticationFailedException e
    ){
        return ResponseEntity.status(401).body(e.getMessage());
    }

    @ExceptionHandler(value = {DataNotFoundException.class})
    public ResponseEntity<String> dataNotFoundExceptionHandler(
            DataNotFoundException e
    ){
        return ResponseEntity.status(401).body(e.getMessage());
    }

    @ExceptionHandler(value = {RequestValidationException.class})
    public ResponseEntity<String> requestValidationExceptionHandler(
            RequestValidationException e
    ){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(value = {UniqueObjectException.class})
    public ResponseEntity<String> uniqueObjectExceptionHandler(
            UniqueObjectException e
    ){
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
