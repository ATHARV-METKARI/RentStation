package com.renstation.common.exception;

/**
 * Conflict exception (e.g., duplicate record).
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
