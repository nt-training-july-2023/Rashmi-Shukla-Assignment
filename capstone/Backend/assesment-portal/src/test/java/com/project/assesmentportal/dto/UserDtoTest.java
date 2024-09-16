package com.project.assesmentportal.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDtoTest {
    UserDto userDto;
    
    @BeforeEach
    void init() {
        userDto  = new UserDto();
    }
    @Test
    void testAllGettersSetters() {
        assertEquals(0, userDto.getUserId());
        assertEquals(null, userDto.getFirstName());
        assertEquals(null, userDto.getLastName());
        assertEquals(null, userDto.getPassword());
        assertEquals(null, userDto.getEmail());
        assertEquals(null, userDto.getPhoneNumber());
        assertEquals("user", userDto.getRole());
        
        userDto.setUserId(12);
        userDto.setFirstName("Rashmi");
        userDto.setLastName("Shukla");
        userDto.setPassword("1234");
        userDto.setEmail("rs@gmail.com");
        userDto.setPhoneNumber("9234567890");
        userDto.setRole("admin");
        
        
        assertEquals(12, userDto.getUserId());
        assertEquals("Rashmi", userDto.getFirstName());
        assertEquals("Shukla", userDto.getLastName());
        assertEquals("1234", userDto.getPassword());
        assertEquals("rs@gmail.com", userDto.getEmail());
        assertEquals("9234567890", userDto.getPhoneNumber());
        assertEquals("admin", userDto.getRole());
        
    }
    
    @Test
    void testDefaultConstructor() {
        UserDto userDto  = new UserDto();
        assertEquals(0, userDto.getUserId());
        assertEquals(null, userDto.getFirstName());
        assertEquals(null, userDto.getLastName());
        assertEquals(null, userDto.getPassword());
        assertEquals(null, userDto.getEmail());
        assertEquals(null, userDto.getPhoneNumber());
    }
    
    @Test
    void testParameterizedConstructor() {
        UserDto userDto = new UserDto(12,"Rashmi","Shukla","1234","rs@gmail.com","9234567890","user");
        assertEquals(12, userDto.getUserId());
        assertEquals("Rashmi", userDto.getFirstName());
        assertEquals("Shukla", userDto.getLastName());
        assertEquals("1234", userDto.getPassword());
        assertEquals("rs@gmail.com", userDto.getEmail());
        assertEquals("9234567890", userDto.getPhoneNumber());
        assertEquals("user", userDto.getRole());
        
    }

}
