package br.com.joncmuniz.microservices.common.web.exception;

public class PreconditionFailedException extends RuntimeException {

    public PreconditionFailedException() {
        super();
    }

    public PreconditionFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PreconditionFailedException(final String message) {
        super(message);
    }

    public PreconditionFailedException(final Throwable cause) {
        super(cause);
    }

}