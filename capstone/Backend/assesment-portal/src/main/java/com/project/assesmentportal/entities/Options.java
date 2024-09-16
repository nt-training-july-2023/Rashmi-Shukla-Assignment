package com.project.assesmentportal.entities;

import com.project.assesmentportal.messages.ErrorConstants;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Options class containing all the options.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Options {
    /**
     * The optionI of the question.
     */
    @NotBlank(message = ErrorConstants.OPTIONI_REQUIRED)
    private String optionI;
    /**
     * The optionII of the question.
     */
    @NotBlank(message = ErrorConstants.OPTIONII_REQUIRED)
    private String optionII;
    /**
     * The optionIII of the question.
     */
    @NotBlank(message = ErrorConstants.OPTIONIII_REQUIRED)
    private String optionIII;
    /**
     * The optionIV of the question.
     */
    @NotBlank(message = ErrorConstants.OPTIONIV_REQUIRED)
    private String optionIV;
}
