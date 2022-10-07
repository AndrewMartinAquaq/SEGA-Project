package com.aquaq.project.SEGAProject.rest.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenericErrorResponseTest {

    @Test
    public void genericErrorResponseSetterTest(){
        int expectedStatus = 200;
        String expectedMessage = "test message";
        Long expectedTimeStamp = 1234L;

        GenericErrorResponse response = new GenericErrorResponse();

        response.setStatus(expectedStatus);
        response.setMessage(expectedMessage);
        response.setTimeStamp(expectedTimeStamp);

        assertEquals(expectedStatus,response.getStatus());
        assertEquals(expectedMessage,response.getMessage());
        assertEquals(expectedTimeStamp, response.getTimeStamp());
    }

    @Test
    public void genericErrorResponseObjectTest(){
        int expectedStatus = 200;
        String expectedMessage = "test message";
        Long expectedTimeStamp = 1234L;

        GenericErrorResponse response = new GenericErrorResponse(expectedStatus, expectedMessage, expectedTimeStamp);

        assertEquals(expectedStatus,response.getStatus());
        assertEquals(expectedMessage,response.getMessage());
        assertEquals(expectedTimeStamp, response.getTimeStamp());
    }

}
