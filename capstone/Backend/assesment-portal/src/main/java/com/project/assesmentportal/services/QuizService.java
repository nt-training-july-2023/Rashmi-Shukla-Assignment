package com.project.assesmentportal.services;

import java.util.List;

import com.project.assesmentportal.dto.ApiResponse;
import com.project.assesmentportal.dto.QuestionDto;
import com.project.assesmentportal.dto.QuizDto;

/**
 * Service interface for managing quiz-related operations.
 */
public interface QuizService {

    /**
     * Adds a new quiz.
     * @param quizDto The QuizDto representing the quiz to be added.
     * @return ApiResponse for the added quiz.
     */
    ApiResponse addQuiz(QuizDto quizDto);

    /**
     * Retrieves a list of all quizzes.
     * @return A list of QuizDto objects representing all quizzes.
     */
    List<QuizDto> getQuizzes();

    /**
     * Retrieves quiz by id.
     * @param quizId of existing quiz.
     * @return QuizDto of specified quizId.
     */
    QuizDto getQuizById(long quizId);

    /**
     * Updates an existing quiz.
     * @param quizDto QuizDto of updated quiz.
     * @param quizId of existing quiz.
     * @return ApiResponse for updated quiz.
     */
    ApiResponse updateQuiz(QuizDto quizDto, long quizId);

    /**
     * delete quiz.
     * @param quizId of existing quiz.
     * @return ApiResponse
     */
    ApiResponse deleteQuiz(long quizId);

    /**
     * Gets list of questions for a quiz.
     * @param quizId id of the quiz.
     * @return list of questions.
     */
    List<QuestionDto> getQuestionsByQuiz(long quizId);
}
