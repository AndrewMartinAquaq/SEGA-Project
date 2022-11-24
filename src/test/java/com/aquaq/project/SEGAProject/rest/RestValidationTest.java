package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.rest.exceptions.InvalidInputException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;

public class RestValidationTest {

    public RestValidation restValidation = new RestValidation();

    @Test
    public void createResponseTest(){
        HttpStatus expectedStatus = HttpStatus.OK;
        String expectedBody = "{ \"Expected\" : 1 }";
        HttpHeaders expectedHeaders = new HttpHeaders();
        expectedHeaders.set("Content-Type", "application/json");

        ResponseEntity<String> expectedResponse = ResponseEntity
                .status(expectedStatus.value())
                .headers(expectedHeaders)
                .body(expectedBody);

        ResponseEntity<String> actualResponse=  restValidation.createResponse(expectedBody, expectedStatus);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void validateGradDateTest() {
        assertThrows(InvalidInputException.class, () -> {
            String wrongSemester = "not 2022";
            restValidation.validateGradDate(wrongSemester);
        });
    }

    @Test
    public void validateGradDateYearLessTest() {
        assertThrows(InvalidInputException.class, () -> {
            String wrongSemester = "1999";
            restValidation.validateGradDate(wrongSemester);
        });
    }

    @Test
    public void validateGradDateYearMoreTest() {
        assertThrows(InvalidInputException.class, () -> {
            String wrongSemester = "3001";
            restValidation.validateGradDate(wrongSemester);
        });
    }

    @Test
    public void validateSemesterTest() {
        assertThrows(InvalidInputException.class, () -> {
            String wrongSemester = "not SUMMER2022";
            restValidation.validateSemester(wrongSemester);
        });
    }

    @Test
    public void validateSemesterYearLessTest() {
        assertThrows(InvalidInputException.class, () -> {
            String wrongSemester = "SUMMER1999";
            restValidation.validateSemester(wrongSemester);
        });
    }

    @Test
    public void validateSemesterYearMoreTest() {
        assertThrows(InvalidInputException.class, () -> {
            String wrongSemester = "SUMMER3001";
            restValidation.validateSemester(wrongSemester);
        });
    }

    @Test
    public void validateNameTest() {
        assertThrows(InvalidInputException.class, () -> {
            String wrongName = "with number 1";
            restValidation.validateName(wrongName);
        });
    }
}
