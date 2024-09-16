package com.project.assesmentportal.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.project.assesmentportal.dto.ApiResponse;

class ApiResponseTest {
    
    ApiResponse apiResponse;
    
    @BeforeEach
    void init() {
        apiResponse = new ApiResponse();
    }
    
    @Test
    void testGettersSetters() {
        assertEquals(null, apiResponse.getMessage());
        assertEquals(0, apiResponse.getStatus());
        assertEquals(null, apiResponse.getErrors());
        
        Map<String, String> errors = new HashMap<>();
        errors.put("error1", "message1");
        errors.put("error2", "message2");
        apiResponse.setErrors(errors);
        apiResponse.setMessage("User not found");
        apiResponse.setStatus(404);

        Map<String, String> result = apiResponse.getErrors();
        assertNotNull(result);
        assertEquals(errors, result);
        assertEquals("User not found", apiResponse.getMessage());
        assertEquals(404, apiResponse.getStatus());
        
    }
    
    @Test
    void testDefaultConstructor() {
        ApiResponse apiResponse = new ApiResponse();
        assertEquals(null, apiResponse.getMessage());
        assertEquals(0, apiResponse.getStatus());
    }
    
    @Test
    void testParameterizedConstructor() {
        ApiResponse apiResponse = new ApiResponse("User not found", 404);
        assertEquals("User not found", apiResponse.getMessage());
        assertEquals(404, apiResponse.getStatus());
    }

    @Test
    void AllArgsConstructor() {
        Map<String, String> errors = new HashMap<>();
        errors.put("error1", "message1");
        errors.put("error2", "message2");

        ApiResponse apiResponse = new ApiResponse("User not found",404,errors);
        
        Map<String, String> result = apiResponse.getErrors();
        assertNotNull(result);
        assertEquals(errors, result);
        assertEquals("User not found", apiResponse.getMessage());
        assertEquals(404, apiResponse.getStatus());
    }

}
