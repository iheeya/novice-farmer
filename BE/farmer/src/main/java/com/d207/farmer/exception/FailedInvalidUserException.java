package com.d207.farmer.exception;

public class FailedInvalidUserException extends RuntimeException {
    public FailedInvalidUserException() {
        super();
    }

    public FailedInvalidUserException(String message) {
        super(message);
    }

    public FailedInvalidUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedInvalidUserException(Throwable cause) {
        super(cause);
    }

    protected FailedInvalidUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
