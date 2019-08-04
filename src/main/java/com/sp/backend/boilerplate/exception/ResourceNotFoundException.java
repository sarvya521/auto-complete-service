package com.sp.backend.boilerplate.exception;

/**
 * Custom Exception specific to handle 404
 *
 * @author sarvesh
 */
public class ResourceNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
