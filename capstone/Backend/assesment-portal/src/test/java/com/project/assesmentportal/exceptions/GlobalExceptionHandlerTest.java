package com.project.assesmentportal.exceptions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.project.assesmentportal.dto.ApiResponse;

class GlobalExceptionHandlerTest {
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;
    
    @Mock
    private ResourceNotFoundException resourceNotFoundException;
    @Mock
    private InvalidDataException invalidDataException;
    @Mock
    private DuplicateResourceException duplicateResourceException;
    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;
    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testResourceNotFoundExceptionHandler() {
        when(resourceNotFoundException.getMessage()).thenReturn("Resource not found");
        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.resourceNotFoundExceptionHandler(resourceNotFoundException);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Resource not found", responseEntity.getBody().getMessage());
    }

    @Test
    public void testInvalidDataExceptionHandler() {
        when(invalidDataException.getMessage()).thenReturn("Invalid data");
        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.invalidDataExceptionHandler(invalidDataException);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Invalid data", responseEntity.getBody().getMessage());
    }

    @Test
    public void testDuplicateResourceExceptionHandler() {
        when(duplicateResourceException.getMessage()).thenReturn("Duplicate resource");
        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.duplicateResourceExceptionHandler(duplicateResourceException);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("Duplicate resource", responseEntity.getBody().getMessage());
    }

    @Test
    public void testHandleMethodArgsNotValidException() {
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(new FieldError("objectName", "fieldName", "Validation error")));

        ResponseEntity<ApiResponse> responseEntity = globalExceptionHandler.handleMethodArgsNotValidException(methodArgumentNotValidException);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Validation failed", responseEntity.getBody().getMessage());
        assertEquals(1, responseEntity.getBody().getErrors().size());
        assertEquals("Validation error", responseEntity.getBody().getErrors().get("fieldName"));
    }
}
