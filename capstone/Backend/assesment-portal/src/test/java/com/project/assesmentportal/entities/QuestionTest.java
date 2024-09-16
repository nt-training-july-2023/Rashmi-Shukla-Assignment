package com.project.assesmentportal.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

public class QuestionTest {

    private Question question;

    @BeforeEach
    public void setUp() {
        question = new Question(1L, "Sample Question", "Option 1", "Option 2", "Option 3", "Option 4", "Option 1");
    }

    @Test
    public void testConstructor() {
        assertEquals(1L, question.getQuestionId());
        assertEquals("Sample Question", question.getQuestionTitle());
        assertEquals("Option 1", question.getOptionOne());
        assertEquals("Option 2", question.getOptionTwo());
        assertEquals("Option 3", question.getOptionThree());
        assertEquals("Option 4", question.getOptionFour());
        assertEquals("Option 1", question.getAnswer());
    }

    @Test
    public void testGettersAndSetters() {
        question.setQuestionId(2L);
        question.setQuestionTitle("Updated Question");
        question.setOptionOne("Updated Option 1");
        question.setOptionTwo("Updated Option 2");
        question.setOptionThree("Updated Option 3");
        question.setOptionFour("Updated Option 4");
        question.setAnswer("Updated Option 1");

        assertEquals(2L, question.getQuestionId());
        assertEquals("Updated Question", question.getQuestionTitle());
        assertEquals("Updated Option 1", question.getOptionOne());
        assertEquals("Updated Option 2", question.getOptionTwo());
        assertEquals("Updated Option 3", question.getOptionThree());
        assertEquals("Updated Option 4", question.getOptionFour());
        assertEquals("Updated Option 1", question.getAnswer());
    }

    @Test
    public void testQuizGetterAndSetter() {
        Category category = new Category(1,"IT","Corporate");
        Quiz quiz = new Quiz(1L, "Sample Quiz", "Description", 60, category);
        question.setQuiz(quiz);

        assertNotNull(question.getQuiz());
        assertEquals(1L, question.getQuiz().getQuizId());
        assertEquals("Sample Quiz", question.getQuiz().getQuizTitle());
        assertEquals("Description", question.getQuiz().getQuizDescription());
        assertEquals(60, question.getQuiz().getQuizTimer());
    }
    
    @Test
    public void testAllArgsConstructor() {
        assertEquals(1L, question.getQuestionId());
        assertEquals("Sample Question", question.getQuestionTitle());
        assertEquals("Option 1", question.getOptionOne());
        assertEquals("Option 2", question.getOptionTwo());
        assertEquals("Option 3", question.getOptionThree());
        assertEquals("Option 4", question.getOptionFour());
        assertEquals("Option 1", question.getAnswer());
    }

    @Test
    public void testDefaultConstructor() {
        Question defaultQuestion = new Question();
        Category category = new Category(1,"IT","Corporate");
        Quiz quiz =  new Quiz(1,"React","Frontend Quiz",20,category);
        defaultQuestion.setQuiz(quiz);
        assertEquals(0L, defaultQuestion.getQuestionId());
        assertNull(defaultQuestion.getQuestionTitle());
        assertNull(defaultQuestion.getOptionOne());
        assertNull(defaultQuestion.getOptionTwo());
        assertNull(defaultQuestion.getOptionThree());
        assertNull(defaultQuestion.getOptionFour());
        assertNull(defaultQuestion.getAnswer());
        assertNotNull(defaultQuestion.getQuiz());
    }
}

