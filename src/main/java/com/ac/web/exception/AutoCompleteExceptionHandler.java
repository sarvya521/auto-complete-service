package com.ac.web.exception;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerMapping;

import com.ac.constant.AcError;
import com.ac.exception.AutoCompleteSvcException;
import com.ac.exception.AutoCompleteSvcNotFoundException;
import com.ac.exception.ErrorDetails;
import com.ac.exception.ResourceNotFoundException;

/**
 * Exception handler to handle API specific exceptions.
 *
 * @author sarvesh
 */
@RestControllerAdvice
public class AutoCompleteExceptionHandler extends CommonResponseEntityExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(AutoCompleteExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        LOGGER.error(ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return handleExceptionInternal(ex, errorDetails, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ AutoCompleteSvcNotFoundException.class })
    public final ResponseEntity<Object> handleResourceNotFound(AutoCompleteSvcNotFoundException ax,
            NativeWebRequest request) {
        LOGGER.error(ax);
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                "auto-complete search is not implemented for requested type " + getPathVariables(request).get("type"),
                request.getDescription(false));
        return handleExceptionInternal(ax, errorDetails, new HttpHeaders(), HttpStatus.NOT_IMPLEMENTED, request);
    }

    @ExceptionHandler({ AutoCompleteSvcException.class })
    public final ResponseEntity<Object> handleGloabalServiceExceptions(AutoCompleteSvcException ax,
            WebRequest request) {
        LOGGER.error(ax);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ax.getError() != null ? ax.getError()
            .msg() : AcError.UNKNOWN_ERROR.msg(), request.getDescription(false));
        return handleExceptionInternal(ax, errorDetails, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({ Exception.class })
    public final ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request) {
        LOGGER.error(ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), AcError.UNKNOWN_ERROR.msg(),
                request.getDescription(false));
        return handleExceptionInternal(ex, errorDetails, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getPathVariables(NativeWebRequest request) {
        HttpServletRequest httpServletRequest = request.getNativeRequest(HttpServletRequest.class);
        return (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    }
}
