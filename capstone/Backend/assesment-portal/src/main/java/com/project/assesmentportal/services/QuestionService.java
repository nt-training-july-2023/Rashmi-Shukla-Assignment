package com.project.assesmentportal.services;

import java.util.List;

import com.project.assesmentportal.dto.ApiResponse;
import com.project.assesmentportal.dto.QuestionDto;

/**
 * Service interface for managing question-related operations.
 */
public interface QuestionService {
    /**
     * adds a new question.
     * @param questionDto of the question to be added.
     * @return QuestionDto
     */
    ApiResponse addQuestion(QuestionDto questionDto);

    /**
     * gets all questions.
     * @return all the questions.
     */
    List<QuestionDto> getQuestions();

    /**
     * updates an existing question.
     * @param questionDto updates question.
     * @param questionId  of the question to be updated.
     * @return updated question.
     */
    ApiResponse updateQuestion(QuestionDto questionDto, long questionId);

    /**
     * get question by question-id.
     * @param questionId of the question.
     * @return question.
     */
    QuestionDto getQuestionById(long questionId);

    /**
     * delete question by Id.
     * @param questionId id of the question to be deleted.
     * @return ApiResponse
     */
    ApiResponse deleteQuestion(long questionId);
}
