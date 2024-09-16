package com.project.assesmentportal.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.assesmentportal.dto.ApiResponse;

/**
 * Global exception handler for handling various exceptions in the
 * application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the ResourceNotFoundException and creates an appropriate API
     * response.
     * @param ex The ResourceNotFoundException that was thrown.
     * @return A ResponseEntity with the corresponding ApiResponse.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(
            final ResourceNotFoundException ex) {
        String message = ex.getMessage();
        ApiResponse apiResponse =
                new ApiResponse(message, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ApiResponse>(apiResponse,
                HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the DuplicateResourceException and creates an appropriate
     * API response.
     * @param ex The DuplicateResourceException that was thrown.
     * @return A ResponseEntity with the corresponding ApiResponse.
     */
    @ExceptionHandler(InvalidDataException.class)
    public final ResponseEntity<ApiResponse> invalidDataExceptionHandler(
            final InvalidDataException ex) {
        String message = ex.getMessage();
        ApiResponse apiResponse =
                new ApiResponse(message, HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<ApiResponse>(apiResponse,
                HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles the DuplicateResourceException and creates an appropriate
     * API response.
     * @param ex The DuplicateResourceException that was thrown.
     * @return A ResponseEntity with the corresponding ApiResponse.
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public final ResponseEntity<ApiResponse> duplicateResourceExceptionHandler(
            final DuplicateResourceException ex) {
        String message = ex.getMessage();
        ApiResponse apiResponse =
                new ApiResponse(message, HttpStatus.CONFLICT.value());
        return new ResponseEntity<ApiResponse>(apiResponse,
                HttpStatus.CONFLICT);
    }

    /**
     * Handles the MethodArgumentNotValidException and creates a response
     * with validation error details.
     * @param ex The MethodArgumentNotValidException that was thrown.
     * @return A ResponseEntity with a map containing field names and
     *         validation error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ApiResponse> handleMethodArgsNotValidException(
            final MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        ApiResponse apiResponse = new ApiResponse("Validation failed",
                HttpStatus.BAD_REQUEST.value(), errors);
        return new ResponseEntity<ApiResponse>(apiResponse,
                HttpStatus.BAD_REQUEST);
    }
}
