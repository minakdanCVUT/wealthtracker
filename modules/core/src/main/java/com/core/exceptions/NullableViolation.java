package com.core.exceptions;

public class NullableViolation extends RuntimeException {
    public NullableViolation() {
        super("You have to fill all fields");
    }
}
