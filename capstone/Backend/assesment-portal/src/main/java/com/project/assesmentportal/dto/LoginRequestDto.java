package com.project.assesmentportal.dto;

import com.project.assesmentportal.messages.ErrorConstants;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The DTO class representing the Login Request in the assessment
 * platform.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    /**
     * The Email Address of the User.
     */
    @NotBlank(message = ErrorConstants.EMAIL_REQUIRED)
    private String email;
    /**
     * The Password of the User.
     */
    @NotBlank(message = ErrorConstants.PASSWORD_REQUIRED)
    private String password;
}
