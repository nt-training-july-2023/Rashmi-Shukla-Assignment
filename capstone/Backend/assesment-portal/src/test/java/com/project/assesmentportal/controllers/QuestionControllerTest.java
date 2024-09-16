package com.project.assesmentportal.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.assesmentportal.dto.ApiResponse;
import com.project.assesmentportal.dto.CategoryDto;
import com.project.assesmentportal.dto.QuestionDto;
import com.project.assesmentportal.dto.QuizDto;
import com.project.assesmentportal.entities.Options;
import com.project.assesmentportal.messages.MessageConstants;
import com.project.assesmentportal.services.impl.QuestionServiceImpl;

class QuestionControllerTest {

    @InjectMocks
    private QuestionController questionController;
    
    @Mock
    private QuestionServiceImpl questionService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testAddQuestion() {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionId(1);
        questionDto.setQuestionTitle("2+2=?");
        Options options = new Options("2", "4", "5", "6");
        questionDto.setOptions(options);
        questionDto.setAnswer(options.getOptionII());
        
        ApiResponse apiResponse = new ApiResponse(MessageConstants.QUESTION_ADDED_SUCCESSFULLY, HttpStatus.CREATED.value());
        when(questionService.addQuestion(questionDto)).thenReturn(apiResponse);
        
        ResponseEntity<ApiResponse> expectedResponse = new ResponseEntity<ApiResponse>(apiResponse,
                HttpStatus.CREATED);
        
        ResponseEntity<ApiResponse> response = questionController.addQuestion(questionDto);
        assertEquals(expectedResponse, response);
    }
    
    @Test
    public void testGetQuestions() {
        List<QuestionDto> questions = new ArrayList<>();
        CategoryDto categoryDto = new CategoryDto(1,"IT","Corporate");
        QuizDto quizDto = new QuizDto(1,"Gk","quiz",20,categoryDto);
        questions.add(new QuestionDto(1L, "question",new Options("1","2","3","4"),"4", quizDto) );
        
        when(questionService.getQuestions()).thenReturn(questions);
        
        List<QuestionDto> response = questionController.getQuestions();
        assertEquals(questions, response);
    }
    
    @Test
    public void testGetQuiz() {
        long questionId = 1;
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionId(1);
        questionDto.setQuestionTitle("2+2=?");
        Options options = new Options("2", "4", "5", "6");
        questionDto.setOptions(options);
        questionDto.setAnswer(options.getOptionII());
        
        when(questionService.getQuestionById(questionId)).thenReturn(questionDto);
        
        ResponseEntity<QuestionDto> expectedResponse = new ResponseEntity<QuestionDto>(questionDto,
                HttpStatus.OK);
        
        ResponseEntity<QuestionDto> response = questionController.getQuestionById(questionId);
        assertEquals(expectedResponse, response);
    }
    
    @Test
    public void testUpdateQuestion() {
        long questionId = 1;
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionId(1);
        questionDto.setQuestionTitle("2+2=?");
        Options options = new Options("2", "4", "5", "6");
        questionDto.setOptions(options);
        questionDto.setAnswer(options.getOptionII());
        
        ApiResponse apiResponse = new ApiResponse(MessageConstants.QUESTION_UPDATED_SUCCESSFULLY, HttpStatus.OK.value());
        when(questionService.updateQuestion(questionDto,questionId)).thenReturn(apiResponse);
        
        ResponseEntity<ApiResponse> expectedResponse = new ResponseEntity<ApiResponse>(apiResponse,
                HttpStatus.OK);
        
        ResponseEntity<ApiResponse> response = questionController.updateQuestion(questionDto, questionId);
        assertEquals(expectedResponse, response);
    }
    
    @Test
    public void testDeleteQuestion() {
        long questionId = 1L;
        
        ApiResponse apiResponse = new ApiResponse(MessageConstants.QUESTION_DELETED_SUCCESSFULLY, HttpStatus.OK.value());
        when(questionService.deleteQuestion(questionId)).thenReturn(apiResponse);
        
        ResponseEntity<ApiResponse> expectedResponse = new ResponseEntity<ApiResponse>(apiResponse,
                HttpStatus.OK);
        
        ResponseEntity<ApiResponse> response = questionController.deleteQuestion(questionId);
        assertEquals(expectedResponse, response);
    }

}
