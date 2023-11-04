package com.renstation.common.exception;

/**
 * Forbidden exception.
 */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
