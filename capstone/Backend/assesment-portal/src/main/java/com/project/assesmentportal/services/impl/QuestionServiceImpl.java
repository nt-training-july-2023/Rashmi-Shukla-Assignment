package com.project.assesmentportal.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.assesmentportal.dto.CategoryDto;
import com.project.assesmentportal.dto.QuestionDto;
import com.project.assesmentportal.dto.QuizDto;
import com.project.assesmentportal.entities.Category;
import com.project.assesmentportal.entities.Options;
import com.project.assesmentportal.entities.Question;
import com.project.assesmentportal.entities.Quiz;
import com.project.assesmentportal.exceptions.ResourceNotFoundException;
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
     * instance of ModelMapper.
     */
    @Autowired
    private ModelMapper modelMapper;

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
    private static final Logger LOGGER = LoggerFactory
            .getLogger(QuestionServiceImpl.class);

    /**
     * add new question.
     * @param questionDto QuestionDto of question.
     * @return returns QuestionDto of added question.
     */
    @Override
    public final String addQuestion(final QuestionDto questionDto) {
        Question question = this.dtoToEntity(questionDto);
        Optional<Quiz> existingQuiz = quizRepository
                .findById(question.getQuiz().getQuizId());
        if (existingQuiz.isEmpty()) {
            LOGGER.error("Add Question: quiz not found"
                    + question.getQuiz().getQuizId());
            throw new ResourceNotFoundException("Quiz with id:"
                    + question.getQuiz().getQuizId() + " doesnot exists");
        }
        questionRepository.save(question);
        LOGGER.info("Question added successfully.");
        return "Question added successfully!";
    }

    /**
     * gets all questions.
     * @return list of questions
     */
    @Override
    public final List<QuestionDto> getQuestions() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionDto> questionDtos = questions.stream()
                .map(this::entityToDto).collect(Collectors.toList());
        LOGGER.info("Retrieved a list of question successfully.");
        return questionDtos;
    }

    /**
     * updates existing question.
     * @param questionDto QuestionDto of quiz
     * @param questionId  Question id of existing quiz.
     * @return updated quiz.
     */
    @Override
    public final String updateQuestion(final QuestionDto questionDto,
            final long questionId) {
        Question exisitingQuestion = questionRepository
                .findById(questionId).orElseGet(() -> {
                    LOGGER.error(" Update Question: question not found"
                            + questionId);
                    throw new ResourceNotFoundException(
                            "Question doesnot exists");
                });

        Question question = dtoToEntity(questionDto);

        Optional<Quiz> existingQuiz = quizRepository
                .findById(question.getQuiz().getQuizId());
        if (existingQuiz.isEmpty()) {
            LOGGER.error(" Update Question: quiz not found");
            throw new ResourceNotFoundException("Quiz with id:"
                    + question.getQuiz().getQuizId() + " doesnot exists");
        }

        exisitingQuestion.setQuestionTitle(question.getQuestionTitle());
        exisitingQuestion.setOptionOne(question.getOptionOne());
        exisitingQuestion.setOptionTwo(question.getOptionTwo());
        exisitingQuestion.setOptionThree(question.getOptionThree());
        exisitingQuestion.setOptionFour(question.getOptionFour());
        exisitingQuestion.setAnswer(question.getAnswer());
        exisitingQuestion.setQuiz(question.getQuiz());

        questionRepository.save(exisitingQuestion);
        LOGGER.info("Question with ID: " + questionId
                + " Updated successfully.");
        return "Question with id: " + questionId
                + " updated successfully!";
    }

    /**
     * get question by its id.
     * @param questionId id of the question return question dto.
     */
    @Override
    public final QuestionDto getQuestionById(final long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseGet(() -> {
                    LOGGER.error("Get Question: question not found"
                            + questionId);
                    throw new ResourceNotFoundException(
                            "Question doesnot exists!");
                });
        LOGGER.info("Retrieved a question with ID: " + questionId
                + " successfully.");
        return this.entityToDto(question);
    }

    /**
     * deletes question by id.
     * @param questionId id of the question to delete.
     */
    @Override
    public final void deleteQuestion(final long questionId) {
        questionRepository.findById(questionId).orElseGet(() -> {
            LOGGER.error(
                    "Delete Question: question not found" + questionId);
            throw new ResourceNotFoundException(
                    "Question doesnot exists!");
        });
        LOGGER.info("Question with ID: " + questionId
                + " deleted successfully.");
        questionRepository.deleteById(questionId);
    }

    /**
     * converts dto to entity.
     * @param questionDto QuestionDto to convert
     * @return converted question.
     */
    public final Question dtoToEntity(final QuestionDto questionDto) {
        Question question = modelMapper.map(questionDto, Question.class);
        question.setOptionOne(questionDto.getOptions().getOptionI());
        question.setOptionTwo(questionDto.getOptions().getOptionII());
        question.setOptionThree(questionDto.getOptions().getOptionIII());
        question.setOptionFour(questionDto.getOptions().getOptionIV());
        // check if answer belongs to options
        boolean found = false;
        String correctAnswerMatch = questionDto.getAnswer();
        if (correctAnswerMatch
                .equalsIgnoreCase(questionDto.getOptions().getOptionI())
                || correctAnswerMatch.equalsIgnoreCase(
                        questionDto.getOptions().getOptionII())
                || correctAnswerMatch.equalsIgnoreCase(
                        questionDto.getOptions().getOptionIII())
                || correctAnswerMatch.equalsIgnoreCase(
                        questionDto.getOptions().getOptionIV())) {
            found = true;
        }
        if (!found) {
            throw new ResourceNotFoundException(
                    "Correct Answer doesn't match with the options");
        }
        question.setAnswer(correctAnswerMatch);

        if (questionDto.getQuiz() != null) {
            Quiz quiz = modelMapper.map(questionDto.getQuiz(), Quiz.class);
            if (questionDto.getQuiz().getCategory() != null) {
                Category category = modelMapper.map(
                        questionDto.getQuiz().getCategory(),
                        Category.class);
                quiz.setCategory(category);
            }
            question.setQuiz(quiz);
        }
        return question;
    }

    /**
     * converts question to questionDto.
     * @param question Question entity.
     * @return questionDto.
     */
    public final QuestionDto entityToDto(final Question question) {
        // Map the QuizDto to a Quiz entity
        QuestionDto questionDto = modelMapper.map(question,
                QuestionDto.class);
        Options options = new Options(question.getOptionOne(),
                question.getOptionTwo(), question.getOptionThree(),
                question.getOptionFour());
        questionDto.setOptions(options);
        if (question.getQuiz() != null) {
            QuizDto quizDto = modelMapper.map(question.getQuiz(),
                    QuizDto.class);
            if (question.getQuiz().getCategory() != null) {
                CategoryDto categoryDto = modelMapper.map(
                        question.getQuiz().getCategory(),
                        CategoryDto.class);
                quizDto.setCategory(categoryDto);
            }
            questionDto.setQuiz(quizDto);
        }
        return questionDto;
    }

}
