package com.project.assesmentportal.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.project.assesmentportal.entities.Options;

class QuestionDtoTest {

    private QuestionDto questionDto;

    @BeforeEach
    public void setUp() {
        Options options = new Options("Option 1", "Option 2", "Option 3", "Option 4");
        CategoryDto categoryDto = new CategoryDto(1,"IT","Corporate");
        QuizDto quizDto = new QuizDto(1L, "Sample Quiz", "Description", 60, categoryDto);
        questionDto = new QuestionDto(1L, "Sample Question", options, "Option 1", quizDto);
    }

    @Test
    public void testAllArgsConstructor() {
        assertEquals(1L, questionDto.getQuestionId());
        assertEquals("Sample Question", questionDto.getQuestionTitle());

        Options options = questionDto.getOptions();
        assertNotNull(options);
        assertEquals("Option 1", options.getOptionI());
        assertEquals("Option 2", options.getOptionII());
        assertEquals("Option 3", options.getOptionIII());
        assertEquals("Option 4", options.getOptionIV());

        assertEquals("Option 1", questionDto.getAnswer());

        QuizDto quizDto = questionDto.getQuiz();
        assertNotNull(quizDto);
        assertEquals(1L, quizDto.getQuizId());
        assertEquals("Sample Quiz", quizDto.getQuizTitle());
        assertEquals("Description", quizDto.getQuizDescription());
        assertEquals(60, quizDto.getQuizTimer());
    }

    @Test
    public void testDefaultConstructor() {
        QuestionDto defaultQuestionDto = new QuestionDto();
        CategoryDto categoryDto = new CategoryDto(1,"IT","Corporate");
        QuizDto quizDto =  new QuizDto(1,"React","Frontend Quiz",20,categoryDto);
        defaultQuestionDto.setQuiz(quizDto);

        assertEquals(0L, defaultQuestionDto.getQuestionId());
        assertNull(defaultQuestionDto.getQuestionTitle());
        assertNull(defaultQuestionDto.getAnswer());
        assertNotNull(defaultQuestionDto.getQuiz());
    }

    @Test
    public void testGettersAndSetters() {
        QuestionDto newQuestionDto = new QuestionDto();

        newQuestionDto.setQuestionId(2L);
        newQuestionDto.setQuestionTitle("Updated Question");
        Options updatedOptions = new Options("Updated Option 1", "Updated Option 2", "Updated Option 3", "Updated Option 4");
        newQuestionDto.setOptions(updatedOptions);
        newQuestionDto.setAnswer("Updated Option 1");
        CategoryDto categoryDto = new CategoryDto(1,"IT","Corporate");
        QuizDto updatedQuizDto = new QuizDto(2L, "Updated Quiz", "Updated Description", 120, categoryDto);
        newQuestionDto.setQuiz(updatedQuizDto);

        assertEquals(2L, newQuestionDto.getQuestionId());
        assertEquals("Updated Question", newQuestionDto.getQuestionTitle());

        Options retrievedOptions = newQuestionDto.getOptions();
        assertNotNull(retrievedOptions);
        assertEquals("Updated Option 1", retrievedOptions.getOptionI());
        assertEquals("Updated Option 2", retrievedOptions.getOptionII());
        assertEquals("Updated Option 3", retrievedOptions.getOptionIII());
        assertEquals("Updated Option 4", retrievedOptions.getOptionIV());

        assertEquals("Updated Option 1", newQuestionDto.getAnswer());

        QuizDto retrievedQuizDto = newQuestionDto.getQuiz();
        assertNotNull(retrievedQuizDto);
        assertEquals(2L, retrievedQuizDto.getQuizId());
        assertEquals("Updated Quiz", retrievedQuizDto.getQuizTitle());
        assertEquals("Updated Description", retrievedQuizDto.getQuizDescription());
        assertEquals(120, retrievedQuizDto.getQuizTimer());
    }

}
