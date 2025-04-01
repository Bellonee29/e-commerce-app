package com.waystech.authmanagement.exceptions.classes;

public class OtpExpiredException extends RuntimeException{
    public OtpExpiredException(String message) {
        super(message);
    }
}
