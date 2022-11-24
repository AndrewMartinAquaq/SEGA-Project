package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.rest.exceptions.GenericErrorResponse;
import com.aquaq.project.SEGAProject.rest.exceptions.InvalidInputException;
import com.aquaq.project.SEGAProject.rest.exceptions.RecordNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RestExceptionHandlerTest {

    RestExceptionHandler restExceptionHandler = new RestExceptionHandler();

    @Test
    public void GenericHandlerTest(){
        String message = "Generic Exception";

        Exception exception = mock(Exception.class);

        when(exception.getMessage()).thenReturn(message);
        ResponseEntity<GenericErrorResponse> response = restExceptionHandler.handleException(exception);

        verify(exception, times(1)).getMessage();

        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    public void InvalidInputHandlerTest(){
        String message = "Input invalid";

        InvalidInputException exception = mock(InvalidInputException.class);

        when(exception.getMessage()).thenReturn(message);

        ResponseEntity<GenericErrorResponse> response = restExceptionHandler.handleException(exception);

        verify(exception, times(1)).getMessage();

        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    public void RecordNotFoundHandlerTest(){
        String message = "Record not found";

        RecordNotFoundException exception = mock(RecordNotFoundException.class);

        when(exception.getMessage()).thenReturn(message);

        ResponseEntity<GenericErrorResponse> response = restExceptionHandler.handleException(exception);

        verify(exception, times(1)).getMessage();

        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
    }

    @Test
    public void ArgumentNotValidTest(){
        String message = "All fields are required";

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);

        ResponseEntity<GenericErrorResponse> response = restExceptionHandler.handleException(exception);

        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    public void HttpMessageNotReadableTest(){
        String message = "Value must be a number and valid";

        HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);

        ResponseEntity<GenericErrorResponse> response = restExceptionHandler.handleException(exception);

        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    public void DataIntegrityViolationTest(){
        String message = "Invalid Input: ensure credits are between 0 and 20 and the course name dose not already exist in the semester";

        DataIntegrityViolationException exception = mock(DataIntegrityViolationException.class);

        ResponseEntity<GenericErrorResponse> response = restExceptionHandler.handleException(exception);

        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

}
