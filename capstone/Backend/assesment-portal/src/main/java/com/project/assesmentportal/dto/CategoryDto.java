package com.project.assesmentportal.dto;

import com.project.assesmentportal.messages.ErrorConstants;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) class representing a category.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    /**
     * The unique identifier of the category.
     */
    private long categoryId;

    /**
     * The title of the category.
     */
    @NotBlank(message = ErrorConstants.CATEGORY_TITLE_REQUIRED)
    private String categoryTitle;

    /**
     * The description of the category.
     */
    @NotBlank(message = ErrorConstants.CATEGORY_DESCRIPTION_REQUIRED)
    private String categoryDescription;
}
