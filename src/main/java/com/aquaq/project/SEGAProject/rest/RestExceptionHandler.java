package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.rest.exceptions.GenericErrorResponse;
import com.aquaq.project.SEGAProject.rest.exceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<GenericErrorResponse> handleException(Exception e){

        GenericErrorResponse error = new GenericErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<GenericErrorResponse> handleException(RecordNotFoundException e){

        GenericErrorResponse error = new GenericErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
