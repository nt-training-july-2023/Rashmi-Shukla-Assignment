package com.project.assesmentportal.dto;

import com.project.assesmentportal.messages.ErrorConstants;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ResultDto for Result entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto {
    /**
     * The unique identifier of the result.
     */
    private long resultId;

    /**
     * totalMarks of result.
     */
    @Min(value = 0, message = ErrorConstants.TOTAL_MARKS_MIN)
    private int totalMarks;

    /**
     * The obtainedMarks in result.
     */
    @Min(value = 0, message = ErrorConstants.OBTAINED_MARKS_MIN)
    private int obtainedMarks;

    /**
     * The number of attempted questions.
     */
    @Min(value = 0, message = ErrorConstants.ATTEMPTED_QUESTIONS_MIN)
    private int attemptedQuestions;

    /**
     * The total number of questions.
     */
    private int totalQuestions;

    /**
     * The date and time.
     */
    @NotBlank(message = ErrorConstants.DATE_TIME_REQUIRED)
    private String dateTime;

    /**
     * email of an user.
     */
    @NotBlank(message = ErrorConstants.EMAIL_REQUIRED)
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@nucleusteq\\.com$",
            message = ErrorConstants.USER_EMAIL_DOMAIN)
    private String userEmail;

    /**
     * name of the user.
     */
    @NotBlank(message = ErrorConstants.USER_NAME_REQUIRED)
    private String userName;

    /**
     * The title of the quiz to which result belongs.
     */
    @NotBlank(message = ErrorConstants.QUIZ_TITLE_REQUIRED)
    private String quizTitle;

    /**
     * The title of the category to which quiz belongs.
     */
    @NotBlank(message = ErrorConstants.CATEGORY_TITLE_REQUIRED)
    private String categoryTitle;
}
