package com.renstation.common.exception;

/**
 * Validation exception.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
