package com.renstation.common.exception;

/**
 * Generic business logic exception.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
