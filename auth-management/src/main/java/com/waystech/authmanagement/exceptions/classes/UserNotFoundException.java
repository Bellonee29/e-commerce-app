package com.waystech.authmanagement.exceptions.classes;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
