package org.partypal.commonModule.exceptions.classes;

public class OtpNotFoundException extends RuntimeException{
    public OtpNotFoundException(String message) {
        super(message);
    }
}
