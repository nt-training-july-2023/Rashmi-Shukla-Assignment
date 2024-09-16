package com.project.assesmentportal.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.assesmentportal.dto.ApiResponse;
import com.project.assesmentportal.dto.LoginRequestDto;
import com.project.assesmentportal.dto.LoginResponseDto;
import com.project.assesmentportal.dto.UserDto;
import com.project.assesmentportal.messages.MessageConstants;
import com.project.assesmentportal.services.UserService;

import jakarta.validation.Valid;

/**
 * Controller class responsible for user registration, login, and retrieval
 * of user data.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * instance of UserService.
     */
    @Autowired
    private UserService userService;

    /**
     * This creates Logger Instance.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(UserController.class);

    /**
     * Registers a new user.
     * @param userDto The UserDto representing the user to be registered.
     * @return A ResponseEntity with a success message upon successful
     *         registration or a bad request response in case of failure.
     */
    @PostMapping("/register")
    public final ResponseEntity<ApiResponse> register(
            @RequestBody @Valid final UserDto userDto) {
        LOGGER.info(MessageConstants.USER_REGISTRATION_INVOKED);
        ApiResponse response = userService.register(userDto);
        LOGGER.info(MessageConstants.USER_REGISTERED_SUCCESSFULLY);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves a list of all users.
     * @return A list of UserDto objects representing all registered users.
     */
    @GetMapping()
    public final List<UserDto> getUsers() {
        LOGGER.info(MessageConstants.GET_USERS_INVOKED);
        List<UserDto> usersDtos =  userService.getUsers();
        LOGGER.info(MessageConstants.USERS_RETRIEVED_SUCCESSFULLY);
        return usersDtos;
    }

    /**
     * Handles user login.
     * @param loginRequestDto representing the user's login
     *                credentials.
     * @return A ResponseEntity with a success message containing the
     *         user's role upon successful login, or an unauthorized
     *         response in case of failure.
     */
    @PostMapping("/login")
    public final ResponseEntity<LoginResponseDto> login(
            @RequestBody @Valid final LoginRequestDto loginRequestDto) {
        LOGGER.info(MessageConstants.USER_LOGIN_INVOKED);
        LoginResponseDto loginResponseDto = userService.login(loginRequestDto);
        LOGGER.info(MessageConstants.USER_LOGGED_IN_SUCCESSFULLY);
        return ResponseEntity.ok(loginResponseDto);

    }
}
