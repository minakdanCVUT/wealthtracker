package com.core.exceptions.password;

public class BadLengthException extends RuntimeException {
    public BadLengthException(String message) {
        super(message);
    }
}
