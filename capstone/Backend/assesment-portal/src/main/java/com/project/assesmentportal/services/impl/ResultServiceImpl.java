package com.project.assesmentportal.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.assesmentportal.dto.ApiResponse;
import com.project.assesmentportal.dto.ResultDto;
import com.project.assesmentportal.entities.Result;
import com.project.assesmentportal.messages.MessageConstants;
import com.project.assesmentportal.repositories.ResultRepository;
import com.project.assesmentportal.services.ResultService;

/**
 * Implementation of the ResultService interface for managing
 * result-related operations.
 */
@Service
public class ResultServiceImpl implements ResultService {

    /**
     * instance of ModelMapper.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * instance of QuizRepository interface.
     */
    @Autowired
    private ResultRepository resultRepository;

    /**
     * Creating a instance of Logger Class.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ResultServiceImpl.class);

    /**
     * Adds a new result.
     * @param resultDto The ResultDTO representing the Result to be added.
     * @return String for successful operation.
     */
    @Override
    public final ApiResponse addResult(final ResultDto resultDto) {
        LOGGER.info(MessageConstants.ADD_RESULT_INVOKED);
        Result result = this.modelMapper.map(resultDto, Result.class);
        resultRepository.save(result);
        LOGGER.info(MessageConstants.ADD_RESULT_ENDED);
        ApiResponse apiResponse = new ApiResponse(
                MessageConstants.RESULT_ADDED_SUCCESSFULLY,
                HttpStatus.CREATED.value());
        return apiResponse;
    }

    /**
     * Retrieves a list of all results.
     * @return A list of ResultDto objects representing all results.
     */
    @Override
    public final List<ResultDto> getResults() {
        LOGGER.info(MessageConstants.GET_RESULTS_INVOKED);
        List<Result> results = this.resultRepository.findAll();
        List<ResultDto> resultDtos = results.stream().map(
                (result) -> this.modelMapper.map(result, ResultDto.class))
                .collect(Collectors.toList());
        LOGGER.info(MessageConstants.GET_RESULTS_ENDED);
        return resultDtos;
    }

    /**
     * Retrieves a result by user's email.
     * @param userEmail The email of the user.
     * @return List of ResultDto of the retrieved results.
     */
    @Override
    public final List<ResultDto> getResultByUserEmail(final String userEmail) {
        LOGGER.info(MessageConstants.GET_RESULT_INVOKED);
        List<Result> results = this.resultRepository
                .findByUserEmail(userEmail);
        List<ResultDto> resultDtos = results.stream().map(
                (result) -> this.modelMapper.map(result, ResultDto.class))
                .collect(Collectors.toList());
        LOGGER.info(MessageConstants.GET_RESULT_ENDED);
        return resultDtos;
    }

}
