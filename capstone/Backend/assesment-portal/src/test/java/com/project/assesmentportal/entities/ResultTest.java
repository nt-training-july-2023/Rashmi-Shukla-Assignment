package com.project.assesmentportal.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ResultTest {

    @Test
    public void testGettersAndSetters() {
        Result result = new Result();
        result.setResultId(1L);
        result.setTotalMarks(100);
        result.setObtainedMarks(85);
        result.setAttemptedQuestions(25);
        result.setTotalQuestions(30);
        result.setDateTime("2023-09-23 14:30:00");
        result.setUserEmail("user@example.com");
        result.setUserName("John Doe");
        result.setQuizTitle("Sample Quiz");
        result.setCategoryTitle("Sample Category");
        
        assertEquals(1L, result.getResultId());
        assertEquals(100, result.getTotalMarks());
        assertEquals(85, result.getObtainedMarks());
        assertEquals(25, result.getAttemptedQuestions());
        assertEquals(30, result.getTotalQuestions());
        assertEquals("2023-09-23 14:30:00", result.getDateTime());
        assertEquals("user@example.com", result.getUserEmail());
        assertEquals("John Doe", result.getUserName());
        assertEquals("Sample Quiz", result.getQuizTitle());
        assertEquals("Sample Category", result.getCategoryTitle());
    }

    @Test
    public void testResultEntityNoArgsConstructor() {
        Result result = new Result();

        assertEquals(0,result.getResultId());
        assertEquals(0, result.getTotalMarks());
        assertEquals(0, result.getObtainedMarks());
        assertEquals(0, result.getAttemptedQuestions());
        assertEquals(0, result.getTotalQuestions());
        assertNull(result.getDateTime());
        assertNull(result.getUserEmail());
        assertNull(result.getUserName());
        assertNull(result.getQuizTitle());
        assertNull(result.getCategoryTitle());
    }
    
    @Test
    public void testAllArgsConstructor() {
        Result anotherResult = new Result(
                2L,      // resultId
                90,      // totalMarks
                75,      // obtainedMarks
                20,      // attemptedQuestions
                25,      // totalQuestions
                "2023-09-24 15:45:00", // dateTime
                "user2@example.com",    // userEmail
                "Jane Doe",            // userName
                "Another Quiz",        // quizTitle
                "Another Category"     // categoryTitle
        );
        assertNotNull(anotherResult);
    }

}
