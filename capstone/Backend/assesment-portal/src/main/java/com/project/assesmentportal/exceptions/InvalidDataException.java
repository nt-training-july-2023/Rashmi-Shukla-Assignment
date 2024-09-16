package com.project.assesmentportal.exceptions;

/**
 * Exception indicating that a credential sent is invalid.
 */
public class InvalidDataException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of InvalidDataException
     * with the specified detail message.
     * @param message The detail message explaining the cause of the exception.
     */
    public InvalidDataException(final String message) {
        super(message);
    }
}
