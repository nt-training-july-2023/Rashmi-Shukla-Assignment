package com.project.assesmentportal.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.assesmentportal.dto.ApiResponse;
import com.project.assesmentportal.dto.ResultDto;
import com.project.assesmentportal.messages.MessageConstants;
import com.project.assesmentportal.services.ResultService;

import jakarta.validation.Valid;

/**
 * Controller class responsible for handling CRUD operations.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/result")
public class ResultController {

    /**
     * instance of resultService.
     */
    @Autowired
    private ResultService resultService;

    /**
     * Creating a instance of Logger Class.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(ResultController.class);

    /**
     * handles add result.
     * @param resultDto ResultDto to be added.
     * @return ResponseEntity<String>
     */
    @PostMapping()
    public final ResponseEntity<ApiResponse> addResult(
            @RequestBody @Valid final ResultDto resultDto) {
        LOGGER.info(MessageConstants.ADD_RESULT_INVOKED);
        ApiResponse addResult = this.resultService.addResult(resultDto);
        LOGGER.info(MessageConstants.RESULT_ADDED_SUCCESSFULLY);
        return new ResponseEntity<ApiResponse>(addResult,
                HttpStatus.CREATED);
    }

    /**
     * handles get all results.
     * @return List of all ResultDto.
     */
    @GetMapping()
    public final List<ResultDto> getResults() {
        LOGGER.info(MessageConstants.GET_RESULTS_INVOKED);
        List<ResultDto> resultDtos = resultService.getResults();
        LOGGER.info(MessageConstants.RESULTS_RETRIEVED_SUCCESSFULLY);
        return resultDtos;
    }

    /**
     * handles get results by user email.
     * @param userEmail email of a user.
     * @return list of results for user with that email.
     */
    @GetMapping("/{userEmail}")
    public final List<ResultDto> getResultByUserEmail(
            @PathVariable final String userEmail) {
        LOGGER.info(MessageConstants.GET_RESULT_INVOKED);
        List<ResultDto> resultDtos =
                resultService.getResultByUserEmail(userEmail);
        LOGGER.info(MessageConstants.RESULT_RETRIEVED_SUCCESSFULLY
                + userEmail);
        return resultDtos;
    }

}
