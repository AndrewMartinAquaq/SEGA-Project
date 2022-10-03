package com.aquaq.project.SEGAProject.rest.exceptions;

public class RecordNotFoundException extends RuntimeException{

    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordNotFoundException(Throwable cause) {
        super(cause);
    }
}
