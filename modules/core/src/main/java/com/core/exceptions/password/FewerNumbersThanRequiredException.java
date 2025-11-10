package com.core.exceptions.password;

public class FewerNumbersThanRequiredException extends RuntimeException {
    public FewerNumbersThanRequiredException(String message) {
        super(message);
    }
}
