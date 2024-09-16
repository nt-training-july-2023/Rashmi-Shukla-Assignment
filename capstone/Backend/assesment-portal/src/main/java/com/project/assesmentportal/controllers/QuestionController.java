package com.project.assesmentportal.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.assesmentportal.dto.ApiResponse;
import com.project.assesmentportal.dto.QuestionDto;
import com.project.assesmentportal.messages.MessageConstants;
import com.project.assesmentportal.services.QuestionService;

import jakarta.validation.Valid;

/**
 * Controller class responsible for handling CRUD operations.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/question")
public class QuestionController {

    /**
     * instance of questionService.
     */
    @Autowired
    private QuestionService questionService;

    /**
     * The instance of Logger Class.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(QuestionController.class);

    /**
     * handle add question.
     * @param questionDto of question to be added.
     * @return added quiz.
     */
    @PostMapping()
    public final ResponseEntity<ApiResponse> addQuestion(
            @RequestBody @Valid final QuestionDto questionDto) {
        LOGGER.info(MessageConstants.ADD_QUESTION_INVOKED);
        ApiResponse addQuestion =
                this.questionService.addQuestion(questionDto);
        LOGGER.info(MessageConstants.QUESTION_ADDED_SUCCESSFULLY);
        return new ResponseEntity<ApiResponse>(addQuestion,
                HttpStatus.CREATED);
    }

    /**
     * gets list of all questions.
     * @return list of questions.
     */
    @GetMapping()
    public final List<QuestionDto> getQuestions() {
        LOGGER.info(MessageConstants.GET_QUESTIONS_INVOKED);
        List<QuestionDto> questionDtos = questionService.getQuestions();
        LOGGER.info(MessageConstants.QUESTIONS_RETRIEVED_SUCCESSFULLY);
        return questionDtos;
    }

    /**
     * gets question by id.
     * @param questionId id of the question to be updated.
     * @return QuestionDto.
     */
    @GetMapping("/{id}")
    public final ResponseEntity<QuestionDto> getQuestionById(
            @PathVariable("id") final long questionId) {
        LOGGER.info(MessageConstants.GET_QUESTION_INVOKED);
        QuestionDto questionDto =
                questionService.getQuestionById(questionId);
        LOGGER.info(MessageConstants.QUESTIONS_RETRIEVED_SUCCESSFULLY
                + questionId);
        return new ResponseEntity<QuestionDto>(questionDto, HttpStatus.OK);
    }

    /**
     * updates question.
     * @param questionDto QuestionDto of new question.
     * @param questionId  id of the question
     * @return QuestionDto.
     */
    @PutMapping("/{id}")
    public final ResponseEntity<ApiResponse> updateQuestion(
            @RequestBody @Valid final QuestionDto questionDto,
            @PathVariable("id") final long questionId) {
        LOGGER.info(MessageConstants.UPDATE_QUESTION_INVOKED);
        ApiResponse response =
                questionService.updateQuestion(questionDto, questionId);
        LOGGER.info(MessageConstants.QUESTION_UPDATED_SUCCESSFULLY);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    /**
     * deletes Question.
     * @param questionId id of the question to be deleted.
     * @return string
     */
    @DeleteMapping("/{id}")
    public final ResponseEntity<ApiResponse> deleteQuestion(
            @PathVariable("id") final long questionId) {
        LOGGER.info(MessageConstants.DELETE_QUESTION_INVOKED);
        ApiResponse response = questionService.deleteQuestion(questionId);
        LOGGER.info(MessageConstants.QUESTION_DELETED_SUCCESSFULLY);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
}
