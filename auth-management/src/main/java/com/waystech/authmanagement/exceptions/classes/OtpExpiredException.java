package org.partypal.commonModule.exceptions.classes;

public class OtpExpiredException extends RuntimeException{
    public OtpExpiredException(String message) {
        super(message);
    }
}
