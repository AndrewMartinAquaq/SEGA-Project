package com.aquaq.project.SEGAProject.rest.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecordNotFoundExceptionTest {

    @Test
    public void throwsRecordNotFoundExceptionMessageTest(){

        String expectedMessage = "Record in courses not found at - 1";

        Exception exception = assertThrows(RecordNotFoundException.class, () -> {
            throw new RecordNotFoundException(expectedMessage);
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void throwsRecordNotFoundExceptionMessageAndThrowableTest(){

        String expectedMessage = "Record in courses not found at - 1";

        Exception exception = assertThrows(RecordNotFoundException.class, () -> {
            throw new RecordNotFoundException(expectedMessage, new Throwable());
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void throwsRecordNotFoundExceptionThrowableTest(){

        String expectedMessage = "Record in courses not found at - 1";

        Exception exception = assertThrows(RecordNotFoundException.class, () -> {
            throw new RecordNotFoundException(new Throwable(expectedMessage));
        });

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
