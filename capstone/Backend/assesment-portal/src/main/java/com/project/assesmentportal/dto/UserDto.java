package com.project.assesmentportal.dto;

import com.project.assesmentportal.messages.ErrorConstants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) class representing a user.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    /**
     * The unique identifier of the user.
     */
    private long userId;

    /**
     * The first name of the user.
     */
    @NotBlank(message = ErrorConstants.FIRST_NAME_REQUIRED)
    @Pattern(regexp = "^[a-zA-Z]*$", message =
            ErrorConstants.FIRST_NAME_PATTERN)
    private String firstName;

    /**
     * The last name of the user.
     */
    @Pattern(regexp = "^[a-zA-Z]*$", message =
            ErrorConstants.LAST_NAME_PATTERN)
    private String lastName;

    /**
     * The password of the user.
     */

    @NotBlank(message = ErrorConstants.PASSWORD_REQUIRED)
    private String password;

    /**
     * The email address of the user.
     */
    @NotBlank(message = ErrorConstants.EMAIL_REQUIRED)
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@nucleusteq\\.com$",
        message = ErrorConstants.USER_EMAIL_DOMAIN)
    private String email;

    /**
     * The phone number of the user.
     */
    @NotBlank(message = ErrorConstants.PHONE_NUMBER_REQUIRED)
    @Pattern(regexp = "^\\d{10}$", message =
            ErrorConstants.PHONE_NUMBER_PATTERN)
    private String phoneNumber;

    /**
     * The role of the user. Default value is "user".
     */
    private String role = "user";
}
