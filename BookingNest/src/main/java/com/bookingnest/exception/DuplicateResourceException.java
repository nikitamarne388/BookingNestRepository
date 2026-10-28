package com.bookingnest.exception;

/**
 * Thrown when creating or updating would break a uniqueness rule (e.g. category name already exists).
 * Translated to HTTP 409 by GlobalExceptionHandler.
 */
public class DuplicateResourceException extends RuntimeException {

    private String message;
    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public DuplicateResourceException(String message){
        super(message);
    }

    public DuplicateResourceException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s already exists with %s: %s", resourceName, fieldName, fieldValue));
    }

}
