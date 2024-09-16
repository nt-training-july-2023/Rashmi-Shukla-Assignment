
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
import com.project.assesmentportal.dto.QuestionDto;
import com.project.assesmentportal.dto.QuizDto;
import com.project.assesmentportal.entities.Category;
import com.project.assesmentportal.entities.Options;
import com.project.assesmentportal.entities.Question;
import com.project.assesmentportal.entities.Quiz;
import com.project.assesmentportal.exceptions.DuplicateResourceException;
import com.project.assesmentportal.exceptions.ResourceNotFoundException;
import com.project.assesmentportal.messages.ErrorConstants;
import com.project.assesmentportal.messages.MessageConstants;
import com.project.assesmentportal.repositories.CategoryRepository;
import com.project.assesmentportal.repositories.QuizRepository;
import com.project.assesmentportal.services.QuizService;

/**
 * Implementation of the QuizService interface for managing quiz-related
 * operations.
 */
@Service
public class QuizServiceImpl implements QuizService {

    /**
     * instance of ModelMapper.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * instance of CategoryRepository.
     */
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * instance of QuizRepository interface.
     */
    @Autowired
    private QuizRepository quizRepository;

    /**
     * Creating a instance of Logger Class.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(QuizServiceImpl.class);

    /**
     * Adds a new quiz.
     * @param quizDto The QuizDto representing the quiz to be added.
     * @return The QuizDto of the added category.
     * @throws DuplicateResourceException If a quiz with the same title
     *                                    already exists.
     */
    @Override
    public final ApiResponse addQuiz(final QuizDto quizDto) {
        LOGGER.info(MessageConstants.ADD_QUIZ_INVOKED);
        Quiz quiz = this.dtoToEntity(quizDto);
        Optional<Category> checkExistingCategory = categoryRepository
                .findById(quiz.getCategory().getCategoryId());
        if (checkExistingCategory.isEmpty()) {
            LOGGER.error(ErrorConstants.CATEGORY_DOESNOT_EXISTS
                    + quiz.getCategory().getCategoryId());
            throw new ResourceNotFoundException(
                    ErrorConstants.CATEGORY_DOESNOT_EXISTS
                            + quiz.getCategory().getCategoryId());
        }
        Optional<Quiz> checkExistingQuiz =
                quizRepository.findByQuizTitle(quiz.getQuizTitle());
        if (checkExistingQuiz.isPresent()) {
            LOGGER.error(ErrorConstants.QUIZ_ALREADY_EXISTS);
            throw new DuplicateResourceException(
                    ErrorConstants.QUIZ_ALREADY_EXISTS);
        }
        quizRepository.save(quiz);
        LOGGER.info(MessageConstants.ADD_QUIZ_ENDED);
        ApiResponse apiResponse =
                new ApiResponse(MessageConstants.QUIZ_ADDED_SUCCESSFULLY,
                        HttpStatus.CREATED.value());
        return apiResponse;
    }

    /**
     * Retrieves a list of all quizzes.
     * @return A list of QuizDto objects representing all quizzes.
     */
    @Override
    public final List<QuizDto> getQuizzes() {
        LOGGER.info(MessageConstants.GET_QUIZZES_INVOKED);
        List<Quiz> quizzes = this.quizRepository.findAll();
        List<QuizDto> quizDtos =
                quizzes.stream().map((quiz) -> this.entityToDto(quiz))
                        .collect(Collectors.toList());
        LOGGER.info(MessageConstants.GET_QUIZZES_ENDED);
        return quizDtos;
    }

    /**
     * Retrieves a quiz by its ID.
     * @param quizId The ID of the quiz to retrieve.
     * @return The QuizDto of the retrieved quiz.
     * @throws ResourceNotFoundException If the quiz with the specified ID
     *                                   does not exist.
     */
    @Override
    public final QuizDto getQuizById(final long quizId) {
        LOGGER.info(MessageConstants.GET_QUIZ_INVOKED);
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        if (quiz.isPresent()) {
            LOGGER.info(MessageConstants.GET_QUIZ_ENDED);
            return this.entityToDto(quiz.get());
        } else {
            LOGGER.error(ErrorConstants.QUIZ_DOESNOT_EXISTS + quizId);
            throw new ResourceNotFoundException(
                    ErrorConstants.QUIZ_DOESNOT_EXISTS + quizId);
        }
    }

    /**
     * Updates a quiz.
     * @param quizDto The updated QuizDto.
     * @param quizId  The ID of the quiz to update.
     * @return The QuizDto of the updated category.
     * @throws ResourceNotFoundException If the quiz with the specified ID
     *                                   does not exist.
     */
    @Override
    public final ApiResponse updateQuiz(final QuizDto quizDto,
            final long quizId) {
        LOGGER.info(MessageConstants.UPDATE_QUIZ_INVOKED);
        Quiz exisitingQuiz =
                quizRepository.findById(quizId).orElseGet(() -> {
                    LOGGER.error(
                            ErrorConstants.QUIZ_DOESNOT_EXISTS + quizId);
                    throw new ResourceNotFoundException(
                            ErrorConstants.QUIZ_DOESNOT_EXISTS + quizId);
                });

        Optional<Category> checkExistingCategory = categoryRepository
                .findById(quizDto.getCategory().getCategoryId());
        if (checkExistingCategory.isEmpty()) {
            LOGGER.error(ErrorConstants.CATEGORY_DOESNOT_EXISTS
                    + quizDto.getCategory().getCategoryId());
            throw new ResourceNotFoundException(
                    ErrorConstants.CATEGORY_DOESNOT_EXISTS
                            + quizDto.getCategory().getCategoryId());
        }

        // Checks if the updated title is the same as the existing one
        if (!quizDto.getQuizTitle().equals(exisitingQuiz.getQuizTitle())) {
            // Title has changed, checks for duplicates
            Optional<Quiz> checkExistingQuiz =
                    quizRepository.findByQuizTitle(quizDto.getQuizTitle());
            if (checkExistingQuiz.isPresent()) {

                LOGGER.error(ErrorConstants.QUIZ_ALREADY_EXISTS);
                throw new DuplicateResourceException(
                        ErrorConstants.QUIZ_ALREADY_EXISTS);
            }
        }

        exisitingQuiz.setQuizTitle(quizDto.getQuizTitle());
        exisitingQuiz.setQuizDescription(quizDto.getQuizDescription());
        exisitingQuiz.setQuizTimer(quizDto.getQuizTimer());

        Category updatedCategory =
                new Category(quizDto.getCategory().getCategoryId(),
                        quizDto.getCategory().getCategoryTitle(),
                        quizDto.getCategory().getCategoryDescription());

        exisitingQuiz.setCategory(updatedCategory);
        quizRepository.save(exisitingQuiz);
        LOGGER.info(MessageConstants.UPDATE_QUIZ_ENDED);
        ApiResponse apiResponse =
                new ApiResponse(MessageConstants.QUIZ_UPDATED_SUCCESSFULLY,
                        HttpStatus.OK.value());
        return apiResponse;
    }

    /**
     * Deletes a quiz by its ID.
     * @param quizId The ID of the quiz to delete.
     * @throws ResourceNotFoundException If the quiz with the specified ID
     *                                   doesnot exist.
     */
    @Override
    public final ApiResponse deleteQuiz(final long quizId) {
        LOGGER.info(MessageConstants.DELETE_QUIZ_INVOKED);
        quizRepository.findById(quizId).orElseGet(() -> {
            LOGGER.error(ErrorConstants.QUIZ_DOESNOT_EXISTS + quizId);
            throw new ResourceNotFoundException(
                    ErrorConstants.QUIZ_DOESNOT_EXISTS + quizId);
        });
        quizRepository.deleteById(quizId);
        LOGGER.info(MessageConstants.DELETE_QUIZ_ENDED);
        ApiResponse apiResponse =
                new ApiResponse(MessageConstants.QUIZ_DELETED_SUCCESSFULLY,
                        HttpStatus.OK.value());
        return apiResponse;
    }

    /**
     * gets questions of this quiz.
     * @param quizId id of the quiz.
     * @return list of questions.
     */
    @Override
    public final List<QuestionDto> getQuestionsByQuiz(final long quizId) {
        LOGGER.info(MessageConstants.GET_QUESTIONS_BY_QUIZ_INVOKED);
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> {
            LOGGER.error(ErrorConstants.QUIZ_DOESNOT_EXISTS + quizId);
            throw new ResourceNotFoundException(
                    ErrorConstants.QUIZ_DOESNOT_EXISTS + quizId);
        });
        List<Question> questions = quiz.getQuestions();
        LOGGER.info(MessageConstants.GET_QUESTIONS_BY_QUIZ_INVOKED);
        return questions.stream().map(this::questionEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * converts quiz to quizDto.
     * @param quizDto QuizDto to be converted.
     * @return quiz.
     */
    public final Quiz dtoToEntity(final QuizDto quizDto) {
        // Map the QuizDto to a Quiz entity
        Quiz quiz = modelMapper.map(quizDto, Quiz.class);
        if (quizDto.getCategory() != null) {
            Category category = new Category(
                    quizDto.getCategory().getCategoryId(),
                    quizDto.getCategory().getCategoryTitle(),
                    quizDto.getCategory().getCategoryDescription());
            quiz.setCategory(category);
        }
        return quiz;
    }

    /**
     * converts quizDto to quiz.
     * @param quiz Quiz to be converted.
     * @return quizDto.
     */
    public final QuizDto entityToDto(final Quiz quiz) {
        QuizDto quizDto = modelMapper.map(quiz, QuizDto.class);
        if (quiz.getCategory() != null) {
            CategoryDto categoryDto =
                    new CategoryDto(quiz.getCategory().getCategoryId(),
                            quiz.getCategory().getCategoryTitle(),
                            quiz.getCategory().getCategoryDescription());
            quizDto.setCategory(categoryDto);
        }
        return quizDto;
    }

    /**
     * converts question to questionDto.
     * @param question Question entity.
     * @return questionDto.
     */
    public final QuestionDto questionEntityToDto(final Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionId(question.getQuestionId());
        questionDto.setQuestionTitle(question.getQuestionTitle());
        Options options = new Options(question.getOptionOne(),
                question.getOptionTwo(), question.getOptionThree(),
                question.getOptionFour());
        questionDto.setOptions(options);
        questionDto.setAnswer(question.getAnswer());
        QuizDto quizDto = new QuizDto();
        quizDto.setQuizId(question.getQuiz().getQuizId());
        quizDto.setQuizTitle(question.getQuiz().getQuizTitle());
        quizDto.setQuizDescription(
                question.getQuiz().getQuizDescription());
        quizDto.setQuizTimer(question.getQuiz().getQuizTimer());
        CategoryDto categoryDto = new CategoryDto(
                question.getQuiz().getCategory().getCategoryId(),
                question.getQuiz().getCategory().getCategoryTitle(),
                question.getQuiz().getCategory().getCategoryDescription());
        quizDto.setCategory(categoryDto);
        questionDto.setQuiz(quizDto);
        return questionDto;
    }
}
