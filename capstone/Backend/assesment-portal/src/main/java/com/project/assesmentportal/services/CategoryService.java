package com.project.assesmentportal.services;

import java.util.List;

import com.project.assesmentportal.dto.ApiResponse;
import com.project.assesmentportal.dto.CategoryDto;
import com.project.assesmentportal.dto.QuizDto;

/**
 * Service interface for managing category-related operations.
 */
public interface CategoryService {

    /**
     * Adds a new category.
     * @param categoryDto The CategoryDto representing the category to be added.
     * @return The String message for the added category.
     */
    ApiResponse addCategory(CategoryDto categoryDto);

    /**
     * Retrieves a list of all categories.
     * @return A list of CategoryDto objects representing all categories.
     */
    List<CategoryDto> getCategories();

    /**
     * Retrieves a category by its ID.
     * @param categoryId The ID of the category to retrieve.
     * @return The CategoryDto of the retrieved category.
     */
    CategoryDto getCategoryById(long categoryId);

    /**
     * Updates a category.
     * @param categoryDto The updated CategoryDto.
     * @param categoryId  The ID of the category to update.
     * @return String message for the updated category.
     */
    ApiResponse updateCategory(CategoryDto categoryDto, long categoryId);

    /**
     * Deletes a category by its ID.
     * @param categoryId The ID of the category to delete.
     * @return ApiResponse.
     */
    ApiResponse deleteCategory(long categoryId);

    /**
     * Gets list of quizzes for a category.
     * @param categoryId if of the category.
     * @return list of quizzes.
     */
    List<QuizDto> getQuizzesByCategory(long categoryId);
}
