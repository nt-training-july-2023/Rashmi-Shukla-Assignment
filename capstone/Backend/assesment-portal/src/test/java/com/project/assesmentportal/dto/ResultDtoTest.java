package com.project.assesmentportal.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResultDtoTest {

    private ResultDto resultDto;

    @BeforeEach
    public void setUp() {
        resultDto = new ResultDto(
                1L,      // resultId
                100,     // totalMarks
                85,      // obtainedMarks
                25,      // attemptedQuestions
                30,      // totalQuestions
                "2023-09-23 14:30:00", // dateTime
                "user@example.com",    // userEmail
                "John Doe",            // userName
                "Sample Quiz",         // quizTitle
                "Sample Category"      // categoryTitle
        );
    }

    @Test
    public void testResultDtoGettersAndSetters() {
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

        assertEquals(2L, resultDto.getResultId());
        assertEquals(90, resultDto.getTotalMarks());
        assertEquals(75, resultDto.getObtainedMarks());
        assertEquals(20, resultDto.getAttemptedQuestions());
        assertEquals(25, resultDto.getTotalQuestions());
        assertEquals("2023-09-24 15:45:00", resultDto.getDateTime());
        assertEquals("user2@example.com", resultDto.getUserEmail());
        assertEquals("Jane Doe", resultDto.getUserName());
        assertEquals("Another Quiz", resultDto.getQuizTitle());
        assertEquals("Another Category", resultDto.getCategoryTitle());
    }
    
    @Test
    public void testNoArgsConstructor() {
        ResultDto resultDto = new ResultDto();
        assertNotNull(resultDto);
    }

    @Test
    public void testAllArgsConstructor() {
        ResultDto resultDto = new ResultDto(
                1L,      // resultId
                100,     // totalMarks
                85,      // obtainedMarks
                25,      // attemptedQuestions
                30,      // totalQuestions
                "2023-09-23 14:30:00", // dateTime
                "user@example.com",    // userEmail
                "John Doe",            // userName
                "Sample Quiz",         // quizTitle
                "Sample Category"      // categoryTitle
        );
        assertNotNull(resultDto);
    }
}
