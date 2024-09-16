package com.project.assesmentportal.entities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
    User user;
    
    @BeforeEach
    void init() {
        user  = new User();
    }
    @Test
    void testAllGettersSetters() {
        assertEquals(0, user.getUserId());
        assertEquals(null, user.getFirstName());
        assertEquals(null, user.getLastName());
        assertEquals(null, user.getPassword());
        assertEquals(null, user.getEmail());
        assertEquals(null, user.getPhoneNumber());
        assertEquals("user", user.getRole());
        
        user.setUserId(12);
        user.setFirstName("Rashmi");
        user.setLastName("Shukla");
        user.setPassword("1234");
        user.setEmail("rs@gmail.com");
        user.setPhoneNumber("9234567890");
        user.setRole("admin");
        
        
        assertEquals(12, user.getUserId());
        assertEquals("Rashmi", user.getFirstName());
        assertEquals("Shukla", user.getLastName());
        assertEquals("1234", user.getPassword());
        assertEquals("rs@gmail.com", user.getEmail());
        assertEquals("9234567890", user.getPhoneNumber());
        assertEquals("admin", user.getRole());
        
    }
    
    @Test
    void testDefaultConstructor() {
        User user  = new User();
        assertEquals(0, user.getUserId());
        assertEquals(null, user.getFirstName());
        assertEquals(null, user.getLastName());
        assertEquals(null, user.getPassword());
        assertEquals(null, user.getEmail());
        assertEquals(null, user.getPhoneNumber());
    }
    
    @Test
    void testParameterizedConstructor() {
        User user = new User(12,"Rashmi","Shukla","1234","rs@gmail.com","9234567890","user");
        assertEquals(12, user.getUserId());
        assertEquals("Rashmi", user.getFirstName());
        assertEquals("Shukla", user.getLastName());
        assertEquals("1234", user.getPassword());
        assertEquals("rs@gmail.com", user.getEmail());
        assertEquals("9234567890", user.getPhoneNumber());
        assertEquals("user", user.getRole());
        
    }

}
