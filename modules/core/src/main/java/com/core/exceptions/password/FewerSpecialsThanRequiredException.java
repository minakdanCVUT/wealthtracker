package com.core.exceptions.password;

public class FewerSpecialsThanRequiredException extends RuntimeException {
    public FewerSpecialsThanRequiredException(String message) {
        super(message);
    }
}
