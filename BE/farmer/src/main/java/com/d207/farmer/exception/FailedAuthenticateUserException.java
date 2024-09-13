package com.d207.farmer.exception;

public class FailedAuthenticateUserException extends RuntimeException {
    public FailedAuthenticateUserException() {
        super();
    }

    public FailedAuthenticateUserException(String message) {
        super(message);
    }

    public FailedAuthenticateUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedAuthenticateUserException(Throwable cause) {
        super(cause);
    }

    protected FailedAuthenticateUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
