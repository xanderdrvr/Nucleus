package com.nucleus.exception.user;

public class InvalidCredentialsException extends UserException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
