package com.eindopdracht.sparkle.exceptions;

public class BadRequestException extends RuntimeException {
//    Instance Variables
    private static final long serialVersionUID = 1L;
//    Constructor
    public BadRequestException() {
        super();
    }
    public BadRequestException(String message) {
        super(message);
    }
}
