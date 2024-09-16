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
import com.project.assesmentportal.dto.ResultDto;
import com.project.assesmentportal.messages.MessageConstants;
import com.project.assesmentportal.services.impl.ResultServiceImpl;

class ResultControllerTest {
    @InjectMocks
    private ResultController resultController;
    
    @Mock
    private ResultServiceImpl resultService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testAddResult() {
        ResultDto resultDto = new ResultDto();
        ApiResponse apiResponse = new ApiResponse(MessageConstants.RESULT_ADDED_SUCCESSFULLY,
                HttpStatus.CREATED.value());
        when(resultService.addResult(resultDto)).thenReturn(apiResponse);
        
        ResponseEntity<ApiResponse> expectedResponse = new ResponseEntity<ApiResponse>(apiResponse,
                HttpStatus.CREATED);
        
        ResponseEntity<ApiResponse> response = resultController.addResult(resultDto);
        assertEquals(expectedResponse, response);
    }
    
    @Test
    void testGetResults() {
        ResultDto resultDto = new ResultDto();
        resultDto.setResultId(2L);
        resultDto.setTotalMarks(90);
        resultDto.setObtainedMarks(75);
        resultDto.setAttemptedQuestions(20);
        resultDto.setTotalQuestions(25);
        resultDto.setDateTime("2023-09-24 15:45:00");
        resultDto.setUserEmail("user2@example.com");
        resultDto.setUserName("Jane Doe");
        resultDto.setQuizTitle("Another Quiz");
        resultDto.setCategoryTitle("Another Category");
        List<ResultDto> resultDtoList = new ArrayList<>();
        resultDtoList.add(resultDto);
        
        when(resultService.getResults()).thenReturn(resultDtoList);
        
        List<ResultDto> response = resultController.getResults();
        assertEquals(resultDtoList, response);
    }
    
    @Test
    void TestGetResultByUserEmail() {
        String userEmail = "rsh@gmail.com";
        List<ResultDto> resultDtoList = new ArrayList<>();
        resultDtoList.add(new ResultDto());
        
        when(resultService.getResultByUserEmail(userEmail)).thenReturn(resultDtoList);
        
        List<ResultDto> response = resultController.getResultByUserEmail(userEmail);
        assertEquals(resultDtoList, response);
    }

}
