package com.d207.farmer.exception;

public class FailedAuthorizationUserException extends RuntimeException {
    public FailedAuthorizationUserException() {
    }

    public FailedAuthorizationUserException(String message) {
        super(message);
    }
}
