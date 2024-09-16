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
import com.project.assesmentportal.dto.QuizDto;
import com.project.assesmentportal.messages.MessageConstants;
import com.project.assesmentportal.services.QuizService;

import jakarta.validation.Valid;

/**
 * Controller class responsible for handling CRUD operations.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/quiz")
public class QuizController {

    /**
     * instance of quizService.
     */
    @Autowired
    private QuizService quizService;

    /**
     * Creating a instance of Logger Class.
     */
    private static final Logger LOGGER
            = LoggerFactory
                    .getLogger(CategoryController.class);

    /**
     * handle add quiz.
     * @param quizDto quiz to be added.
     * @return added quiz.
     */
    @PostMapping()
    public final ResponseEntity<ApiResponse> addQuiz(
            @RequestBody @Valid final QuizDto quizDto) {
        LOGGER.info(MessageConstants.ADD_QUIZ_INVOKED);
        ApiResponse response = quizService.addQuiz(quizDto);
        LOGGER.info(MessageConstants.QUIZ_ADDED_SUCCESSFULLY);
        return new ResponseEntity<ApiResponse>(response,
                HttpStatus.CREATED);
    }

    /**
     * gets list of all quizzes.
     * @return list of quizzes.
     */
    @GetMapping()
    public final List<QuizDto> getQuizzes() {
        LOGGER.info(MessageConstants.GET_QUIZZES_INVOKED);
        List<QuizDto> quizDtos = quizService.getQuizzes();
        LOGGER.info(MessageConstants.QUIZZES_RETRIEVED_SUCCESSFULLY);
        return quizDtos;
    }

    /**
     * gets quiz by id.
     * @param quizId id of quiz.
     * @return quizDto of quiz.
     */
    @GetMapping("/{id}")
    public final ResponseEntity<QuizDto> getQuizById(
            @PathVariable("id") final long quizId) {
        LOGGER.info(MessageConstants.GET_QUIZ_INVOKED);
        QuizDto quizDto = quizService.getQuizById(quizId);
        LOGGER.info(MessageConstants.QUIZ_RETRIEVED_SUCCESSFULLY + quizId);
        return new ResponseEntity<QuizDto>(quizDto,
                HttpStatus.OK);
    }

    /**
     * updates quiz.
     * @param quizId  of quiz.
     * @param quizDto quiz to add.
     * @return updated QuizDto.
     */
    @PutMapping("/{id}")
    public final ResponseEntity<ApiResponse> updateQuiz(
            @PathVariable("id") final long quizId,
            @RequestBody @Valid final QuizDto quizDto) {
        LOGGER.info(MessageConstants.UPDATE_QUIZ_INVOKED);
        ApiResponse response = quizService.updateQuiz(quizDto, quizId);
        LOGGER.info(MessageConstants.QUIZ_UPDATED_SUCCESSFULLY);
        return new ResponseEntity<ApiResponse>(response,
                HttpStatus.OK);
    }

    /**
     * delete quiz.
     * @param quizId id of the quiz.
     * @return responseEntity.
     */
    @DeleteMapping("/{id}")
    public final ResponseEntity<ApiResponse> deleteQuiz(
            @PathVariable("id") final long quizId) {
        LOGGER.info(MessageConstants.DELETE_QUIZ_INVOKED);
        ApiResponse response = quizService.deleteQuiz(quizId);
        LOGGER.info(MessageConstants.QUIZ_DELETED_SUCCESSFULLY);
        return new ResponseEntity<ApiResponse>(response,
                HttpStatus.OK);
    }

    /**
     * returns questions of a quiz.
     * @param id quizId.
     * @return list of questions ResponseEntity
     */
    @GetMapping("/{id}/questions")
    public final ResponseEntity<List<QuestionDto>> getQuestionsByQuiz(
            @PathVariable("id") final long id) {
        LOGGER.info(MessageConstants.GET_QUESTIONS_BY_QUIZ_INVOKED);
        List<QuestionDto> questionsDTO = quizService.getQuestionsByQuiz(id);
        LOGGER.info(MessageConstants.QUESTIONS_BY_QUIZ_RETRIEVED + id);
        return new ResponseEntity<List<QuestionDto>>(
                questionsDTO, HttpStatus.OK);
    }
}
