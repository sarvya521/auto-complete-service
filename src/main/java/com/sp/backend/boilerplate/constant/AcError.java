package com.sp.backend.boilerplate.constant;

/**
 * Enum holding all error messages.
 *
 * @author sarvesh
 */
public enum AcError {
    INVALID_COMPONENT("Auto complete is not available for this component"),
    UNKNOWN_ERROR("Unkwon error. Please contact support"),
    INVALID_HTTP_METHOD_CALL("Please try with supported Http method"),
    INVALID_REQUEST("Request format do not match. Please check API specification"),
    RESOURCE_NOT_FOUND("Invalid Request. Requested resource not found");

    private final String msg;

    private AcError(String msg) {
        this.msg = msg;
    }

    public String msg() {
        return this.msg;
    }
}
