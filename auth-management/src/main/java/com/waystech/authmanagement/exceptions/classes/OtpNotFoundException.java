package com.waystech.authmanagement.exceptions.classes;

public class OtpNotFoundException extends RuntimeException{
    public OtpNotFoundException(String message) {
        super(message);
    }
}
