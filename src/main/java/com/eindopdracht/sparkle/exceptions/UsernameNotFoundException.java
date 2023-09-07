package com.eindopdracht.sparkle.exceptions;

public class UsernameNotFoundException extends RuntimeException {
//    Instance Variables
    private static final long serialVersionUID = 1L;
//    Constructor
    public UsernameNotFoundException(String username) {
        super("Cannot find user " + username);
    }

}
