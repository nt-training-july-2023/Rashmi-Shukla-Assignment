package com.project.assesmentportal.controllers;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.assesmentportal.dto.ApiResponse;
import com.project.assesmentportal.dto.LoginRequestDto;
import com.project.assesmentportal.dto.LoginResponseDto;
import com.project.assesmentportal.dto.UserDto;
import com.project.assesmentportal.messages.MessageConstants;
import com.project.assesmentportal.repositories.UserRepository;
import com.project.assesmentportal.services.impl.UserServiceImpl;

class UserControllerTest {
    
    @InjectMocks
    private UserController userController;
    
    @Mock
    private UserServiceImpl userService;
    
    @Mock
    private UserRepository userRepository;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testRegister() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("Rashmi");
        
        ApiResponse apiResponse = new ApiResponse(MessageConstants.USER_REGISTERED_SUCCESSFULLY, HttpStatus.CREATED.value());
        when(userService.register(userDto)).thenReturn(apiResponse);
        
        ResponseEntity<ApiResponse> expectedResponse = new ResponseEntity<ApiResponse>(apiResponse,
                HttpStatus.CREATED);
        
        ResponseEntity<ApiResponse> response = userController.register(userDto);
        assertEquals(expectedResponse, response);
    }
    
    @Test
    public void testLogin() {
        LoginRequestDto userDto = new LoginRequestDto(); 
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        
        when(userService.login(userDto)).thenReturn(loginResponseDto);
        
        ResponseEntity<LoginResponseDto> expectedResponse = new ResponseEntity<LoginResponseDto>(loginResponseDto,
                HttpStatus.OK);

        ResponseEntity<LoginResponseDto> response = userController.login(userDto);

        assertEquals(expectedResponse, response);
    }
    
    @Test
    public void testGetUsers() {
        List<UserDto> users = new ArrayList<>();
        
        when(userService.getUsers()).thenReturn(users);
        
        List<UserDto> response = userController.getUsers();
        assertEquals(users, response);
    }

}
