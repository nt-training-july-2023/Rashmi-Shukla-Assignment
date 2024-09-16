package com.project.assesmentportal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The DTO class representing the Login Response in the assessment
 * platform.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    /**
     * The full name of the user.
     */
    private String fullName;
    /**
     * The role of the user.
     */
    private String role;
    /**
     * The email of the user.
     */
    private String email;
    /**
     * The success message of login.
     */
    private String message;
}
