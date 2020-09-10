package br.com.joncmuniz.microservices.common.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when user is forbidden to execute specified operation or access specified data.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(final String message) {
        super(message);
    }

    public ForbiddenException(final Throwable cause) {
        super(cause);
    }

}