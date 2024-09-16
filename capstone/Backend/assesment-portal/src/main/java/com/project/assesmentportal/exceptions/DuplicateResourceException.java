package com.project.assesmentportal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception indicating that a resource duplication conflict has occurred.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateResourceException extends RuntimeException {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new DuplicateResourceException with the specified
     * detail message.
     * @param message The detail message explaining the cause of the
     *                exception.
     */
    public DuplicateResourceException(final String message) {
        super(message);
    }
}
