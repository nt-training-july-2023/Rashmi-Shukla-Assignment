package com.project.assesmentportal.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import com.project.assesmentportal.dto.ApiResponse;
import com.project.assesmentportal.dto.CategoryDto;
import com.project.assesmentportal.dto.QuestionDto;
import com.project.assesmentportal.dto.QuizDto;
import com.project.assesmentportal.entities.Category;
import com.project.assesmentportal.entities.Options;
import com.project.assesmentportal.entities.Question;
import com.project.assesmentportal.entities.Quiz;
import com.project.assesmentportal.exceptions.ResourceNotFoundException;
import com.project.assesmentportal.messages.MessageConstants;
import com.project.assesmentportal.repositories.QuestionRepository;
import com.project.assesmentportal.repositories.QuizRepository;

class QuestionServiceImplTest {

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Mock
    private QuestionRepository questionRepository;
    
    @Mock
    private QuizRepository quizRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testAddQuestion_Success() {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionId(1);
        questionDto.setQuestionTitle("2+2=?");
        Options options = new Options("2", "4", "5", "6");
        questionDto.setOptions(options);
        questionDto.setAnswer(options.getOptionII());
        CategoryDto categoryDto = new CategoryDto(1,"IT","Corporate");
        questionDto.setQuiz(new QuizDto(1,"React","Frontend Quiz",20,categoryDto));

        Question question = new Question();
        question.setQuestionId(1);
        question.setQuestionTitle(questionDto.getQuestionTitle());
        question.setOptionOne(questionDto.getOptions().getOptionI());
        question.setOptionTwo(questionDto.getOptions().getOptionII());
        question.setOptionThree(questionDto.getOptions().getOptionIII());
        question.setOptionFour(questionDto.getOptions().getOptionIV());
        question.setAnswer(questionDto.getAnswer());
        Category category = new Category(1,"IT","Corporate");
        question.setQuiz(new Quiz(1,"React","Frontend Quiz",20,category));
        
        when(quizRepository.findById(question.getQuiz().getQuizId())).thenReturn(Optional.of(new Quiz()));
        when(questionRepository.save(question)).thenReturn(question);
        
        ApiResponse expectedResponse = new ApiResponse(
                MessageConstants.QUESTION_ADDED_SUCCESSFULLY,
                HttpStatus.CREATED.value()
                );

        ApiResponse result = questionService.addQuestion(questionDto);
        assertEquals(expectedResponse, result);
    }
    
    @Test
    void testAddQuestion_QuizNotFound() {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionId(1);
        questionDto.setQuestionTitle("2+2=?");
        Options options = new Options("2", "4", "5", "6");
        questionDto.setOptions(options);
        questionDto.setAnswer(options.getOptionII());
        CategoryDto categoryDto = new CategoryDto(1,"IT","Corporate");
        questionDto.setQuiz(new QuizDto(1,"React","Frontend Quiz",20,categoryDto));

        Question question = new Question();
        question.setQuestionId(1);
        question.setQuestionTitle(questionDto.getQuestionTitle());
        question.setOptionOne(questionDto.getOptions().getOptionI());
        question.setOptionTwo(questionDto.getOptions().getOptionII());
        question.setOptionThree(questionDto.getOptions().getOptionIII());
        question.setOptionFour(questionDto.getOptions().getOptionIV());
        question.setAnswer(questionDto.getAnswer());
        Category category = new Category(1,"IT","Corporate");
        question.setQuiz(new Quiz(1,"React","Frontend Quiz",20,category));
        
        when(quizRepository.findById(question.getQuiz().getQuizId())).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, ()-> {
            questionService.addQuestion(questionDto);
        });
    }

    @Test
    void testAddQuestion_AnswerNotInOptions() {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionId(1);
        questionDto.setQuestionTitle("2+2=?");
        Options options = new Options("2", "4", "5", "6");
        questionDto.setOptions(options);
        questionDto.setAnswer("abc");
        CategoryDto categoryDto = new CategoryDto(1,"IT","Corporate");
        questionDto.setQuiz(new QuizDto(1,"React","Frontend Quiz",20,categoryDto));

        Question question = new Question();
        question.setQuestionId(1);
        question.setQuestionTitle(questionDto.getQuestionTitle());
        question.setOptionOne(questionDto.getOptions().getOptionI());
        question.setOptionTwo(questionDto.getOptions().getOptionII());
        question.setOptionThree(questionDto.getOptions().getOptionIII());
        question.setOptionFour(questionDto.getOptions().getOptionIV());
        question.setAnswer(questionDto.getAnswer());
        Category category = new Category(1,"IT","Corporate");
        Quiz quiz =  new Quiz(1,"React","Frontend Quiz",20,category);
        question.setQuiz(quiz);
        
        assertThrows(ResourceNotFoundException.class, ()-> {
            questionService.addQuestion(questionDto);
        });
    }
    
    @Test
    void testgetQuestions_Success() {
        Question question = new Question(1, "2+2?", "1", "2", "3", "4", "4");
        Category category = new Category(1,"IT","Corporate");
        question.setQuiz(new Quiz(1,"React","Frontend Quiz",20,category));
        List<Question> questions = new ArrayList<>();
        questions.add(question);

        when(questionRepository.findAll()).thenReturn(questions);

        List<QuestionDto> questionDtos = questionService.getQuestions();

        assertNotNull(questionDtos);
        assertEquals(1, questionDtos.size());
    }
    
    @Test
    void testUpdateQuestion_Success() {
        long questionIdToUpdate = 1L;
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionId(1);
        questionDto.setQuestionTitle("2+2=?");
        Options options = new Options("2", "4", "5", "6");
        questionDto.setOptions(options);
        questionDto.setAnswer(options.getOptionII());
        CategoryDto categoryDto = new CategoryDto(1,"IT","Corporate");
        questionDto.setQuiz(new QuizDto(1,"React","Frontend Quiz",20,categoryDto));

        Question question = new Question();
        question.setQuestionId(1);
        question.setQuestionTitle(questionDto.getQuestionTitle());
        question.setOptionOne(questionDto.getOptions().getOptionI());
        question.setOptionTwo(questionDto.getOptions().getOptionII());
        question.setOptionThree(questionDto.getOptions().getOptionIII());
        question.setOptionFour(questionDto.getOptions().getOptionIV());
        question.setAnswer(questionDto.getAnswer());
        Category category = new Category(1,"IT","Corporate");
        question.setQuiz(new Quiz(1,"React","Frontend Quiz",20,category));
        
        when(quizRepository.findById(question.getQuiz().getQuizId())).thenReturn(Optional.of(new Quiz()));
        when(questionRepository.findById(questionIdToUpdate)).thenReturn(Optional.of(question));
        when(questionRepository.save(question)).thenReturn(question);
        
        ApiResponse expectedResponse = new ApiResponse(
                MessageConstants.QUESTION_UPDATED_SUCCESSFULLY,
                HttpStatus.OK.value()
                );
        
        ApiResponse result = questionService.updateQuestion(questionDto, questionIdToUpdate);
        assertEquals(expectedResponse, result);
    }
    
    @Test
    void testUpdateQuiz_QuestionNotFound() {
        long questionIdToUpdate = 1L;
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionId(1);
        questionDto.setQuestionTitle("2+2=?");
        Options options = new Options("2", "4", "5", "6");
        questionDto.setOptions(options);
        questionDto.setAnswer(options.getOptionII());
        CategoryDto categoryDto = new CategoryDto(1,"IT","Corporate");
        questionDto.setQuiz(new QuizDto(1,"React","Frontend Quiz",20,categoryDto));
        
        when(questionRepository.findById(questionIdToUpdate)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            questionService.updateQuestion(questionDto, questionIdToUpdate);
        });
    }
    
    @Test
    void testUpdateQuestion_QuizNotFound() {
        long questionIdToUpdate = 1L;
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionId(1);
        questionDto.setQuestionTitle("2+2=?");
        Options options = new Options("2", "4", "5", "6");
        questionDto.setOptions(options);
        questionDto.setAnswer(options.getOptionII());
        CategoryDto categoryDto = new CategoryDto(1,"IT","Corporate");
        questionDto.setQuiz(new QuizDto(1,"React","Frontend Quiz",20,categoryDto));

        Question question = new Question();
        question.setQuestionId(1);
        question.setQuestionTitle(questionDto.getQuestionTitle());
        question.setOptionOne(questionDto.getOptions().getOptionI());
        question.setOptionTwo(questionDto.getOptions().getOptionII());
        question.setOptionThree(questionDto.getOptions().getOptionIII());
        question.setOptionFour(questionDto.getOptions().getOptionIV());
        question.setAnswer(questionDto.getAnswer());
        Category category = new Category(1,"IT","Corporate");
        question.setQuiz(new Quiz(20,"React","Frontend Quiz",20,category));
        
        when(quizRepository.findById(question.getQuiz().getQuizId())).thenReturn(Optional.empty());
        when(questionRepository.findById(questionIdToUpdate)).thenReturn(Optional.of(question));
        
        assertThrows(ResourceNotFoundException.class, ()-> {
            questionService.updateQuestion(questionDto, questionIdToUpdate);
        });
    }
    
    @Test
    void testGetQuestionById_Success() {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionId(1);
        questionDto.setQuestionTitle("2+2=?");
        Options options = new Options("2", "4", "5", "6");
        questionDto.setOptions(options);
        questionDto.setAnswer(options.getOptionII());
        CategoryDto categoryDto = new CategoryDto(1,"IT","Corporate");
        questionDto.setQuiz(new QuizDto(1,"React","Frontend Quiz",20,categoryDto));

        Question question = new Question();
        question.setQuestionId(1);
        question.setQuestionTitle(questionDto.getQuestionTitle());
        question.setOptionOne(questionDto.getOptions().getOptionI());
        question.setOptionTwo(questionDto.getOptions().getOptionII());
        question.setOptionThree(questionDto.getOptions().getOptionIII());
        question.setOptionFour(questionDto.getOptions().getOptionIV());
        question.setAnswer(questionDto.getAnswer());
        Category category = new Category(1,"IT","Corporate");
        question.setQuiz(new Quiz(1,"React","Frontend Quiz",20,category));
        
        when(questionRepository.findById(question.getQuestionId())).thenReturn(Optional.of(question));
        when(questionRepository.save(any(Question.class))).thenReturn(question);
        
        QuestionDto resultQuestionDto = questionService.getQuestionById(1);
        assertNotNull(resultQuestionDto);
    }
    
    @Test
    void testGetQuestionById_QuestionNotFound() {
        long questionId = 1L;
        when(questionRepository.findById(questionId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            questionService.getQuestionById(1);
        });
    }
    
    @Test
    void testDeleteQuestion_Success(){
        long questionIdToDelete = 1;
        when(questionRepository.findById(questionIdToDelete)).thenReturn(Optional.of(new Question()));
        
        ApiResponse expectedResponse = new ApiResponse(
                MessageConstants.QUESTION_DELETED_SUCCESSFULLY,
                HttpStatus.OK.value()
                );
        
        ApiResponse result = questionService.deleteQuestion(questionIdToDelete);
        verify(questionRepository, times(1)).deleteById(questionIdToDelete);
        assertEquals(expectedResponse, result);
    }
    
    @Test
    void testDeleteQuestion_QuestionNotFound() {
        long questionIdToDelete = 1;
        when(questionRepository.findById(questionIdToDelete)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, ()->{
            questionService.deleteQuestion(questionIdToDelete);
        });
    }

}
