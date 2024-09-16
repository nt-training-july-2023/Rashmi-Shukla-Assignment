package com.project.assesmentportal.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import com.project.assesmentportal.dto.ApiResponse;
import com.project.assesmentportal.dto.ResultDto;
import com.project.assesmentportal.entities.Result;
import com.project.assesmentportal.messages.MessageConstants;
import com.project.assesmentportal.repositories.ResultRepository;

class ResultServiceImplTest {

    @InjectMocks
    private ResultServiceImpl resultService;
    
    @Mock
    private ResultRepository resultRepository;
    
    @Mock
    private ModelMapper modelMapper;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testAddResult_Success() {
        ResultDto resultDto = new ResultDto();
        Result result = new Result();
        when(modelMapper.map(resultDto, Result.class)).thenReturn(result);
        when(resultRepository.save(result)).thenReturn(result);
        
        ApiResponse expectedResponse = new ApiResponse(
                MessageConstants.RESULT_ADDED_SUCCESSFULLY,
                HttpStatus.CREATED.value()
                );
        
        ApiResponse response = resultService.addResult(resultDto);
        assertEquals(response, expectedResponse);
    }
    
    @Test
    void testGetResults_Success() {
        Result result = new Result();
        List<Result> resultList = new ArrayList<>();
        resultList.add(result);
        
        ResultDto resultDto = new ResultDto();
        List<ResultDto> resultDtoList = new ArrayList<>();
        resultDtoList.add(resultDto);
        
        when(resultRepository.findAll()).thenReturn(resultList);
        when(modelMapper.map(result, ResultDto.class)).thenReturn(resultDto);

        List<ResultDto> response = resultService.getResults();

        assertEquals(response, resultDtoList);
    }
    
    @Test 
    void GetResultByUserEmail_Success(){
        String userEmail = "rsh@gmail.com";
        Result result = new Result();
        List<Result> resultList = new ArrayList<>();
        resultList.add(result);
        
        ResultDto resultDto = new ResultDto();
        List<ResultDto> resultDtoList = new ArrayList<>();
        resultDtoList.add(resultDto);
        
        when(resultRepository.findByUserEmail(userEmail)).thenReturn(resultList);
        when(modelMapper.map(result, ResultDto.class)).thenReturn(resultDto);

        List<ResultDto> response = resultService.getResultByUserEmail(userEmail);

        assertEquals(response, resultDtoList);
    }

}
