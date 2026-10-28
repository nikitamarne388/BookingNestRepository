package com.bookingnest.exception;

/**
 * Thrown when the request is well-formed but breaks a business rule,
 * e.g. "a category cannot be its own parent" or "category still has child categories".
 * Translated to HTTP 422 by GlobalExceptionHandler.
 */
public class BusinessRuleException extends RuntimeException {

    private String message;

    public BusinessRuleException(String message) {
        super(message);
    }

}
