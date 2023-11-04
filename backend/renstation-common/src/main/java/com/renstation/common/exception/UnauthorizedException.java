package com.renstation.common.exception;

/**
 * Unauthorized exception.
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
