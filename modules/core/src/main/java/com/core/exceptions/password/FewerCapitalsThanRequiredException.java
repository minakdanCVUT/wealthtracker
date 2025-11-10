package com.core.exceptions.password;

public class FewerCapitalsThanRequiredException extends RuntimeException {
    public FewerCapitalsThanRequiredException(String message) {
        super(message);
    }
}
