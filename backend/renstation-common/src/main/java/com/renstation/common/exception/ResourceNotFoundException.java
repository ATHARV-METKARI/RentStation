package com.renstation.common.exception;

/**
 * Resource not found exception.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
