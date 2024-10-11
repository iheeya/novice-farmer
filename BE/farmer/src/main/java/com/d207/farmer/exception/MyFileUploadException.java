package com.d207.farmer.exception;

public class MyFileUploadException extends RuntimeException {
    public MyFileUploadException() {
    }

    public MyFileUploadException(String message) {
        super(message);
    }

    public MyFileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
