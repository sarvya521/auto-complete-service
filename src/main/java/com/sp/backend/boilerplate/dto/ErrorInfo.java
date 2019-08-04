package com.sp.backend.boilerplate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Wrapper of error details that has to be sent in Api Response
 * 
 * @see com.sp.backend.boilerplate.dto.Response
 * 
 * @since 0.0.1
 * @version 1.0
 * @author sarvesh
 */
@JsonInclude(value = Include.NON_EMPTY, content = Include.NON_NULL)
@JsonPropertyOrder({ "code", "message", "target" })
@Getter
@Setter
@NoArgsConstructor
public class ErrorInfo {
    /**
     * Service Error Code
     */
    private Integer code;
    /**
     * Service Error Message
     */
    private String message;
    /**
     * Error's whereabouts
     */
    private String target;

    /**
     * @param code
     * @param message
     */
    public ErrorInfo(@NonNull Integer code, @NonNull String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @param code
     * @param message
     * @param target
     */
    public ErrorInfo(@NonNull Integer code, @NonNull String message, @NonNull String target) {
        this.code = code;
        this.message = message;
        this.target = target;
    }
}
