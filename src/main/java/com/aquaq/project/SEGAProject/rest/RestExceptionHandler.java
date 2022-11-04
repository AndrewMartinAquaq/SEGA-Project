package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.rest.exceptions.GenericErrorResponse;
import com.aquaq.project.SEGAProject.rest.exceptions.InvalidInputException;
import com.aquaq.project.SEGAProject.rest.exceptions.RecordNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<GenericErrorResponse> handleException(Exception e){

        logger.warn("Generic exception handled - exception of type: " + e.getClass().getName());

        GenericErrorResponse error = new GenericErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<GenericErrorResponse> handleException(InvalidInputException e){

        logger.info("Expected exception handled - exception of type: " + e.getClass().getName());

        GenericErrorResponse error = new GenericErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<GenericErrorResponse> handleException(RecordNotFoundException e){

        logger.info("Expected exception handled - exception of type: " + e.getClass().getName());

        GenericErrorResponse error = new GenericErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<GenericErrorResponse> handleException(MethodArgumentNotValidException e){

        logger.info("Expected exception handled - exception of type: " + e.getClass().getName());

        GenericErrorResponse error = new GenericErrorResponse();

        String message = "All fields are required";

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(message);
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<GenericErrorResponse> handleException(HttpMessageNotReadableException e){

        logger.info("Expected exception handled - exception of type: " + e.getClass().getName());

        GenericErrorResponse error = new GenericErrorResponse();

        String message = "Value must be a number and valid";

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(message);
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<GenericErrorResponse> handleException(DataIntegrityViolationException e){

        logger.info("Expected exception handled - exception of type: " + e.getClass().getName());

        GenericErrorResponse error = new GenericErrorResponse();

        String message = "Value must be a between 0 and 20";

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(message);
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
