package br.com.joncmuniz.microservices.common.web.exception;

public class ValidationException extends RuntimeException {

    public ValidationException(final String message) {
        super(message);
    }

}