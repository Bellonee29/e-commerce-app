package org.partypal.commonModule.exceptions.classes;

public class CustomValidationException extends RuntimeException{
    public CustomValidationException(String message) {
        super(message);
    }
}
