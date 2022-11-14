package com.aquaq.project.SEGAProject.rest.exceptions;

import com.aquaq.project.SEGAProject.dto.EnrollDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvalidInputExceptionTest {

    @Test
    public void exceptionMessageTest(){
        assertThrows(InvalidInputException.class, () -> {
            throw new InvalidInputException("message");
        });
    }

    @Test
    public void exceptionCauseTest(){
        assertThrows(InvalidInputException.class, () -> {
            throw new InvalidInputException(new Throwable("cause"));
        });
    }

    @Test
    public void exceptionMessageAndCauseTest(){
        assertThrows(InvalidInputException.class, () -> {
            throw new InvalidInputException("message", new Throwable("cause"));
        });
    }



}
