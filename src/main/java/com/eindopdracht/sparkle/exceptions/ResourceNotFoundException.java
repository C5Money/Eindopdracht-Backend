package com.eindopdracht.sparkle.exceptions;

public class ResourceNotFoundException extends RuntimeException{
//    Instance Variables
    private static final long serialVersionUID = 1L;


//    Constructor
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
