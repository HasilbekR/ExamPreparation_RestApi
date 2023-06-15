package com.example.exampreparation_restapi.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

public class RequestValidationException extends Throwable {
    private String message;
    public RequestValidationException(List<ObjectError> allErrors) {
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : allErrors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }
            this.message = errorMessage.toString();
        }

        @Override
        public String getMessage() {
            return message;
        }
}
