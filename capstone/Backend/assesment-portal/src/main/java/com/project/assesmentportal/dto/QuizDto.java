package com.project.assesmentportal.dto;

import org.springframework.validation.annotation.Validated;

import com.project.assesmentportal.messages.ErrorConstants;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * quizDto for quiz entity.
 */
@Getter
@Setter
@NoArgsConstructor
@Validated
public class QuizDto {
    /**
     * The unique identifier of the quiz.
     */
    private long quizId;

    /**
     * The title of the quiz.
     */
    @NotBlank(message = ErrorConstants.QUIZ_TITLE_REQUIRED)
    private String quizTitle;

    /**
     * The description of the quiz.
     */
    @NotBlank(message = ErrorConstants.QUIZ_DESCRIPTION_REQUIRED)
    private String quizDescription;

    /**
     * timer for the quiz.
     */
    @Min(value = 1, message = ErrorConstants.QUIZ_TIMER_MIN)
    private int quizTimer;

    /**
     * The category of the quiz.
     */
    @NotNull(message = ErrorConstants.CATEGORY_REQUIRED)
    @Valid
    private CategoryDto category;

    /**
     * get category of quiz.
     * @return category.
     */
    public final CategoryDto getCategory() {
            return new CategoryDto(category.getCategoryId(),
                    category.getCategoryTitle(),
                    category.getCategoryDescription());
    }

    /**
     * set quiz category.
     * @param categoryDto to be set.
     */
    public final void setCategory(final CategoryDto categoryDto) {
            this.category = new CategoryDto(categoryDto.getCategoryId(),
                    categoryDto.getCategoryTitle(),
                    categoryDto.getCategoryDescription());
    }

    /**
     * all args constructor.
     * @param id id of the quiz.
     * @param title title of the quiz.
     * @param description description of the quiz.
     * @param timer timer for the quiz.
     * @param categoryDto of the quiz.
     */
    public QuizDto(final long id, final String title,
            final String description, final int timer,
            final CategoryDto categoryDto) {
        this.quizId = id;
        this.quizTitle = title;
        this.quizDescription = description;
        this.quizTimer = timer;
        this.category = new CategoryDto(categoryDto.getCategoryId(),
            categoryDto.getCategoryTitle(),
            categoryDto.getCategoryDescription());
    }
}
