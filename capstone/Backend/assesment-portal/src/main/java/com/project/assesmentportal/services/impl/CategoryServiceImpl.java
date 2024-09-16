package com.project.assesmentportal.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.assesmentportal.dto.ApiResponse;
import com.project.assesmentportal.dto.CategoryDto;
import com.project.assesmentportal.dto.QuizDto;
import com.project.assesmentportal.entities.Category;
import com.project.assesmentportal.entities.Quiz;
import com.project.assesmentportal.exceptions.DuplicateResourceException;
import com.project.assesmentportal.exceptions.ResourceNotFoundException;
import com.project.assesmentportal.messages.ErrorConstants;
import com.project.assesmentportal.messages.MessageConstants;
import com.project.assesmentportal.repositories.CategoryRepository;
import com.project.assesmentportal.services.CategoryService;

/**
 * Implementation of the CategoryService interface for managing
 * category-related operations.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    /**
     * instance of ModelMapper.
     */
    @Autowired
    private ModelMapper modelMapper;
    /**
     * instance of CategoryRepository interface.
     */
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Creating a instance of Logger Class.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(CategoryServiceImpl.class);

    /**
     * Adds a new category.
     * @param categoryDto The CategoryDto representing the category to be
     *                    added.
     * @return The CategoryDto of the added category.
     * @throws DuplicateResourceException If a category with the same title
     *                                    already exists.
     */
    @Override
    public final ApiResponse addCategory(final CategoryDto categoryDto) {
        LOGGER.info(MessageConstants.ADD_CATEGORY_INVOKED);
        Category category =
                this.modelMapper.map(categoryDto, Category.class);
        Optional<Category> checkExistingCategory = categoryRepository
                .findByCategoryTitle(category.getCategoryTitle());
        if (checkExistingCategory.isPresent()) {
            LOGGER.error(ErrorConstants.CATEGORY_ALREADY_EXISTS);
            throw new DuplicateResourceException(
                    ErrorConstants.CATEGORY_ALREADY_EXISTS);
        }

        this.categoryRepository.save(category);
        LOGGER.info(MessageConstants.ADD_CATEGORY_ENDED);
        ApiResponse apiResponse = new ApiResponse(
                MessageConstants.CATEGORY_ADDED_SUCCESSFULLY,
                HttpStatus.CREATED.value());
        return apiResponse;
    }

    /**
     * Retrieves a list of all categories.
     * @return A list of CategoryDto objects representing all categories.
     */
    @Override
    public final List<CategoryDto> getCategories() {
        LOGGER.info(MessageConstants.GET_CATEGORIES_INVOKED);
        List<Category> categories = this.categoryRepository.findAll();
        List<CategoryDto> categoryDtos =
                categories.stream()
                        .map((category) -> this.modelMapper.map(category,
                                CategoryDto.class))
                        .collect(Collectors.toList());
        LOGGER.info(MessageConstants.GET_CATEGORIES_ENDED);
        return categoryDtos;
    }

    /**
     * Retrieves a category by its ID.
     * @param categoryId The ID of the category to retrieve.
     * @return The CategoryDto of the retrieved category.
     * @throws ResourceNotFoundException If the category with the specified
     *                                   ID does not exist.
     */
    @Override
    public final CategoryDto getCategoryById(final long categoryId) {
        LOGGER.info(MessageConstants.GET_CATEGORY_INVOKED);
        Optional<Category> category =
                categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            LOGGER.info(MessageConstants.GET_CATEGORY_ENDED);
            return this.modelMapper.map(category.get(), CategoryDto.class);
        } else {
            LOGGER.error(
                    ErrorConstants.CATEGORY_DOESNOT_EXISTS + categoryId);
            throw new ResourceNotFoundException(
                    ErrorConstants.CATEGORY_DOESNOT_EXISTS + categoryId);
        }
    }

    /**
     * Updates a category.
     * @param categoryDto The updated CategoryDto.
     * @param categoryId  The ID of the category to update.
     * @return The CategoryDto of the updated category.
     * @throws ResourceNotFoundException If the category with the specified
     *                                   ID does not exist.
     */
    @Override
    public final ApiResponse updateCategory(final CategoryDto categoryDto,
            final long categoryId) {
        LOGGER.info(MessageConstants.UPDATE_CATEGORY_INVOKED);
        Category existingCategory =
                categoryRepository.findById(categoryId).orElseGet(() -> {
                    LOGGER.error(ErrorConstants.CATEGORY_DOESNOT_EXISTS
                            + categoryId);
                    throw new ResourceNotFoundException(
                            ErrorConstants.CATEGORY_DOESNOT_EXISTS
                                    + categoryId);
                });

        // Check if the updated title is the same as the existing one
        if (!categoryDto.getCategoryTitle()
                .equals(existingCategory.getCategoryTitle())) {
            // Title has changed, check for duplicates
            Optional<Category> checkExistingCatgeory = categoryRepository
                    .findByCategoryTitle(categoryDto.getCategoryTitle());
            if (checkExistingCatgeory.isPresent()) {
                LOGGER.error(ErrorConstants.CATEGORY_ALREADY_EXISTS);
                throw new DuplicateResourceException(
                        ErrorConstants.CATEGORY_ALREADY_EXISTS);
            }
        }

        existingCategory.setCategoryTitle(categoryDto.getCategoryTitle());
        existingCategory.setCategoryDescription(
                categoryDto.getCategoryDescription());

        categoryRepository.save(existingCategory);
        LOGGER.info(MessageConstants.UPDATE_CATEGORY_ENDED);
        ApiResponse apiResponse = new ApiResponse(
                MessageConstants.CATEGORY_UPDATED_SUCCESSFULLY,
                HttpStatus.OK.value());
        return apiResponse;
    }

    /**
     * Deletes a category by its ID.
     * @param categoryId The ID of the category to delete.
     * @throws ResourceNotFoundException If the category with the specified
     *                                   ID doesnot exist.
     */
    @Override
    public final ApiResponse deleteCategory(final long categoryId) {
        LOGGER.info(MessageConstants.DELETE_CATEGORY_INVOKED);
        categoryRepository.findById(categoryId).orElseGet(() -> {
            LOGGER.error(
                    ErrorConstants.CATEGORY_DOESNOT_EXISTS + categoryId);
            throw new ResourceNotFoundException(
                    ErrorConstants.CATEGORY_DOESNOT_EXISTS + categoryId);
        });
        categoryRepository.deleteById(categoryId);
        LOGGER.info(MessageConstants.DELETE_CATEGORY_ENDED);
        ApiResponse apiResponse = new ApiResponse(
                MessageConstants.CATEGORY_DELETED_SUCCESSFULLY,
                HttpStatus.OK.value());
        return apiResponse;
    }

    /**
     * gets quizzes of this category.
     * @param categoryId id of the category.
     * @return list of quizzes.
     */
    @Override
    public final List<QuizDto> getQuizzesByCategory(
            final long categoryId) {
        LOGGER.info(MessageConstants.GET_QUIZZES_BY_CATEGORY_INVOKED);
        Category category =
                categoryRepository.findById(categoryId).orElseThrow(() -> {
                    LOGGER.error(ErrorConstants.CATEGORY_DOESNOT_EXISTS
                            + categoryId);
                    throw new ResourceNotFoundException(
                            ErrorConstants.CATEGORY_DOESNOT_EXISTS
                                    + categoryId);
                });
        List<Quiz> quizzes = category.getQuizzes();
        LOGGER.info(MessageConstants.GET_QUIZZES_BY_CATEGORY_ENDED);
        return quizzes.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }

    /**
     * converts quizDto to quiz.
     * @param quiz Quiz to be converted.
     * @return quizDto.
     */
    public final QuizDto entityToDto(final Quiz quiz) {
        QuizDto quizDto = modelMapper.map(quiz, QuizDto.class);
        CategoryDto categoryDto =
                new CategoryDto(quiz.getCategory().getCategoryId(),
                        quiz.getCategory().getCategoryTitle(),
                        quiz.getCategory().getCategoryDescription());
        quizDto.setCategory(categoryDto);
        return quizDto;
    }
}
