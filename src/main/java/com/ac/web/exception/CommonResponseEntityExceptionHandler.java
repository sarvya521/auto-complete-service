package com.ac.web.exception;

import java.util.Date;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ac.constant.AcError;
import com.ac.exception.ErrorDetails;

/**
 * Common exception handler to handle generic rest exceptions
 *
 * @author sarvesh
 */
@RestControllerAdvice
public class CommonResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(AutoCompleteExceptionHandler.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error(ex);
        Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
        if (!CollectionUtils.isEmpty(supportedMethods)) {
            headers.setAllow(supportedMethods);
        }
        ErrorDetails errorDetails = new ErrorDetails(new Date(), AcError.INVALID_HTTP_METHOD_CALL.msg(), request.getDescription(false));
        return handleExceptionInternal(ex, errorDetails, headers, status, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error(ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), AcError.RESOURCE_NOT_FOUND.msg(), request.getDescription(false));
        return handleExceptionInternal(ex, errorDetails, headers, status, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error(ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), AcError.INVALID_REQUEST.msg(), request.getDescription(false));
        return handleExceptionInternal(ex, errorDetails, headers, status, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleServletRequestBindingException(
            ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error(ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), AcError.INVALID_REQUEST.msg(), request.getDescription(false));
        return handleExceptionInternal(ex, errorDetails, headers, status, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error(ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), AcError.INVALID_REQUEST.msg(), request.getDescription(false));
        return handleExceptionInternal(ex, errorDetails, headers, status, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error(ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), AcError.INVALID_REQUEST.msg(), request.getDescription(false));
        return handleExceptionInternal(ex, errorDetails, headers, status, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error(ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), AcError.INVALID_REQUEST.msg(), request.getDescription(false));
        return handleExceptionInternal(ex, errorDetails, headers, status, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error(ex);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), AcError.INVALID_REQUEST.msg(), request.getDescription(false));
        return handleExceptionInternal(ex, errorDetails, headers, status, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), AcError.INVALID_REQUEST.msg(), request.getDescription(false));
        return handleExceptionInternal(ex, errorDetails, headers, status, request);
    }
}
