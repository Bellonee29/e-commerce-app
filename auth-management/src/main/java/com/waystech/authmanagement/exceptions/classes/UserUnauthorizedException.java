package com.waystech.authmanagement.exceptions.classes;

public class UserUnauthorizedException extends  RuntimeException{
    public UserUnauthorizedException(String message) {
        super(message);
    }
}
