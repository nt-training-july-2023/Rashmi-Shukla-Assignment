package com.project.assesmentportal.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.project.assesmentportal.exceptions.ResourceNotFoundException;
import com.project.assesmentportal.messages.ErrorConstants;
import com.project.assesmentportal.messages.MessageConstants;
import com.project.assesmentportal.repositories.QuestionRepository;
import com.project.assesmentportal.repositories.QuizRepository;
import com.project.assesmentportal.services.QuestionService;

/**
 * Implementation of the QuizService interface for managing
 * question-related operations.
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    /**
     * instance of quizRepo.
     */
    @Autowired
    private QuizRepository quizRepository;

    /**
     * instance of QuestionRepositoy.
     */
    @Autowired
    private QuestionRepository questionRepository;

    /**
     * Creating a instance of Logger Class.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(QuestionServiceImpl.class);

    /**
     * add new question.
     * @param questionDto QuestionDto of question.
     * @return returns QuestionDto of added question.
     */
    @Override
    public final ApiResponse addQuestion(final QuestionDto questionDto) {
        LOGGER.info(MessageConstants.ADD_QUESTION_INVOKED);
        Question question = this.dtoToEntity(questionDto);
        Optional<Quiz> existingQuiz =
                quizRepository.findById(question.getQuiz().getQuizId());
        if (existingQuiz.isEmpty()) {
            LOGGER.error(ErrorConstants.QUIZ_DOESNOT_EXISTS
                    + question.getQuiz().getQuizId());
            throw new ResourceNotFoundException(
                    ErrorConstants.QUIZ_DOESNOT_EXISTS
                            + question.getQuiz().getQuizId());
        }
        questionRepository.save(question);
        LOGGER.info(MessageConstants.ADD_QUESTION_ENDED);
        ApiResponse apiResponse = new ApiResponse(
                MessageConstants.QUESTION_ADDED_SUCCESSFULLY,
                HttpStatus.CREATED.value());
        return apiResponse;
    }

    /**
     * gets all questions.
     * @return list of questions
     */
    @Override
    public final List<QuestionDto> getQuestions() {
        LOGGER.info(MessageConstants.GET_QUESTIONS_INVOKED);
        List<Question> questions = questionRepository.findAll();
        List<QuestionDto> questionDtos = questions.stream()
                .map(this::entityToDto).collect(Collectors.toList());
        LOGGER.info(MessageConstants.GET_QUESTIONS_ENDED);
        return questionDtos;
    }

    /**
     * updates existing question.
     * @param questionDto QuestionDto of quiz
     * @param questionId  Question id of existing quiz.
     * @return updated quiz.
     */
    @Override
    public final ApiResponse updateQuestion(final QuestionDto questionDto,
            final long questionId) {
        LOGGER.info(MessageConstants.UPDATE_QUESTION_INVOKED);
        Question exisitingQuestion =
                questionRepository.findById(questionId).orElseGet(() -> {
                    LOGGER.error(ErrorConstants.QUESTION_DOESNOT_EXISTS
                            + questionId);
                    throw new ResourceNotFoundException(
                            ErrorConstants.QUESTION_DOESNOT_EXISTS
                                    + questionId);
                });

        Question question = dtoToEntity(questionDto);

        Optional<Quiz> existingQuiz =
                quizRepository.findById(question.getQuiz().getQuizId());
        if (existingQuiz.isEmpty()) {
            LOGGER.error(ErrorConstants.QUIZ_DOESNOT_EXISTS
                    + question.getQuiz().getQuizId());
            throw new ResourceNotFoundException(
                    ErrorConstants.QUIZ_DOESNOT_EXISTS
                            + question.getQuiz().getQuizId());
        }

        exisitingQuestion.setQuestionTitle(question.getQuestionTitle());
        exisitingQuestion.setOptionOne(question.getOptionOne());
        exisitingQuestion.setOptionTwo(question.getOptionTwo());
        exisitingQuestion.setOptionThree(question.getOptionThree());
        exisitingQuestion.setOptionFour(question.getOptionFour());
        exisitingQuestion.setAnswer(question.getAnswer());
        exisitingQuestion.setQuiz(question.getQuiz());

        questionRepository.save(exisitingQuestion);
        LOGGER.info(MessageConstants.UPDATE_QUESTION_ENDED);
        ApiResponse apiResponse = new ApiResponse(
                MessageConstants.QUESTION_UPDATED_SUCCESSFULLY,
                HttpStatus.OK.value());
        return apiResponse;
    }

    /**
     * get question by its id.
     * @param questionId id of the question return question dto.
     */
    @Override
    public final QuestionDto getQuestionById(final long questionId) {
        LOGGER.info(MessageConstants.GET_QUESTION_INVOKED);
        Question question =
                questionRepository.findById(questionId).orElseGet(() -> {
                    LOGGER.error(ErrorConstants.QUESTION_DOESNOT_EXISTS
                            + questionId);
                    throw new ResourceNotFoundException(
                            ErrorConstants.QUESTION_DOESNOT_EXISTS
                                    + questionId);
                });
        LOGGER.info(MessageConstants.GET_QUESTION_ENDED);
        return this.entityToDto(question);
    }

    /**
     * deletes question by id.
     * @param questionId id of the question to delete.
     */
    @Override
    public final ApiResponse deleteQuestion(final long questionId) {
        LOGGER.info(MessageConstants.DELETE_QUESTION_INVOKED);
        questionRepository.findById(questionId).orElseGet(() -> {
            LOGGER.error(
                    "Delete Question: question not found" + questionId);
            throw new ResourceNotFoundException(
                    ErrorConstants.QUESTION_DOESNOT_EXISTS);
        });
        questionRepository.deleteById(questionId);
        LOGGER.info(MessageConstants.DELETE_QUESTION_ENDED);
        ApiResponse apiResponse = new ApiResponse(
                MessageConstants.QUESTION_DELETED_SUCCESSFULLY,
                HttpStatus.OK.value());
        return apiResponse;
    }

    /**
     * converts dto to entity.
     * @param questionDto QuestionDto to convert
     * @return converted question.
     */
    public final Question dtoToEntity(final QuestionDto questionDto) {
        Question question = new Question();
        question.setQuestionId(questionDto.getQuestionId());
        question.setQuestionTitle(questionDto.getQuestionTitle());
        question.setOptionOne(questionDto.getOptions().getOptionI());
        question.setOptionTwo(questionDto.getOptions().getOptionII());
        question.setOptionThree(questionDto.getOptions().getOptionIII());
        question.setOptionFour(questionDto.getOptions().getOptionIV());
        // check if answer belongs to options
        boolean found = false;
        String correctAnswerMatch = questionDto.getAnswer();
        if (correctAnswerMatch
                .equals(questionDto.getOptions().getOptionI())
                || correctAnswerMatch
                        .equals(questionDto.getOptions().getOptionII())
                || correctAnswerMatch
                        .equals(questionDto.getOptions().getOptionIII())
                || correctAnswerMatch
                        .equals(questionDto.getOptions().getOptionIV())) {
            found = true;
        }
        if (!found) {
            throw new ResourceNotFoundException(
                    ErrorConstants.ANSWER_NOT_IN_OPTIONS);
        }
        question.setAnswer(correctAnswerMatch);

        Quiz quiz = new Quiz();
        quiz.setQuizId(questionDto.getQuiz().getQuizId());
        quiz.setQuizTitle(questionDto.getQuiz().getQuizTitle());
        quiz.setQuizDescription(
                questionDto.getQuiz().getQuizDescription());
        quiz.setQuizTimer(questionDto.getQuiz().getQuizTimer());
        Category category = new Category(
                questionDto.getQuiz().getCategory().getCategoryId(),
                questionDto.getQuiz().getCategory().getCategoryTitle(),
                questionDto.getQuiz().getCategory()
                        .getCategoryDescription());
        quiz.setCategory(category);
        question.setQuiz(quiz);
        return question;
    }

    /**
     * converts question to questionDto.
     * @param question Question entity.
     * @return questionDto.
     */
    public final QuestionDto entityToDto(final Question question) {
        // Map the QuizDto to a Quiz entity
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionId(question.getQuestionId());
        questionDto.setQuestionTitle(question.getQuestionTitle());
        questionDto.setAnswer(question.getAnswer());
        Options options = new Options(question.getOptionOne(),
                question.getOptionTwo(), question.getOptionThree(),
                question.getOptionFour());
        questionDto.setOptions(options);
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
