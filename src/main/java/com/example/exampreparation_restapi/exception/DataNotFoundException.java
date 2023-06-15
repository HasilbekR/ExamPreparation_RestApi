package com.example.exampreparation_restapi.exception;

public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException(String message) {
        super(message);
    }
}
