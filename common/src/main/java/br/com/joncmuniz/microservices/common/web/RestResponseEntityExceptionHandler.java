package br.com.joncmuniz.microservices.common.web;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.joncmuniz.microservices.common.persistence.exception.EntityNotFoundException;
import br.com.joncmuniz.microservices.common.web.exception.ApiError;
import br.com.joncmuniz.microservices.common.web.exception.BadRequestException;
import br.com.joncmuniz.microservices.common.web.exception.ConflictException;
import br.com.joncmuniz.microservices.common.web.exception.ResourceNotFoundException;
import br.com.joncmuniz.microservices.common.web.exception.ValidationErrorDTO;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // 400

    @Override
    protected final ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info("Bad Request: ", ex);
        logger.debug("Bad Request: ", ex);

        final ApiError apiError = message(HttpStatus.BAD_REQUEST, ex);
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected final ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info("Bad Request: ", ex);
        logger.debug("Bad Request: ", ex);

        final BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        final ValidationErrorDTO dto = processFieldErrors(fieldErrors);

        return handleExceptionInternal(ex, dto, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class, BadRequestException.class })
    protected final ResponseEntity<Object> handleBadRequest(final RuntimeException ex, final WebRequest request) {
        logger.info("Bad Request: ", ex);
        logger.debug("Bad Request: ", ex);

        final ApiError apiError = message(HttpStatus.BAD_REQUEST, ex);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // 404

    @ExceptionHandler({ EntityNotFoundException.class, EntityNotFoundException.class, ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        logger.warn("Not Found: ", ex);

        final ApiError apiError = message(HttpStatus.NOT_FOUND, ex);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // 409

    @ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class, ConflictException.class })
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        logger.warn("Conflict: ", ex);

        final ApiError apiError = message(HttpStatus.CONFLICT, ex);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // 415

    @ExceptionHandler({ InvalidMimeTypeException.class, InvalidMediaTypeException.class })
    protected ResponseEntity<Object> handleInvalidMimeTypeException(final IllegalArgumentException ex, final WebRequest request) {
        logger.warn("Unsupported Media Type: ", ex);

        final ApiError apiError = message(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
    }

    // 500

    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handle500s(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);

        final ApiError apiError = message(HttpStatus.INTERNAL_SERVER_ERROR, ex);

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // UTIL

    private ValidationErrorDTO processFieldErrors(final List<FieldError> fieldErrors) {
        final ValidationErrorDTO dto = new ValidationErrorDTO();

        for (final FieldError fieldError : fieldErrors) {
            final String localizedErrorMessage = fieldError.getDefaultMessage();
            dto.addFieldError(fieldError.getField(), localizedErrorMessage);
        }

        return dto;
    }

    private ApiError message(final HttpStatus httpStatus, final Exception ex) {
        final String message = ex.getMessage() == null ? ex.getClass()
            .getSimpleName() : ex.getMessage();
        final String devMessage = ex.getClass()
            .getSimpleName();
        // devMessage = ExceptionUtils.getStackTrace(ex);

        return new ApiError(httpStatus.value(), message, devMessage);
    }

}