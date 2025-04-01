package org.partypal.commonModule.exceptions.classes;

public class UserUnauthorizedException extends  RuntimeException{
    public UserUnauthorizedException(String message) {
        super(message);
    }
}
