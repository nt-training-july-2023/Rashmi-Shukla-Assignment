package com.project.assesmentportal.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryTest {
    Category category;
    
    @BeforeEach
    void init() {
        category = new Category();
    }
    
    @Test
    void testGettersAndSetters() {
        assertEquals(0,category.getCategoryId());
        assertEquals(null, category.getCategoryTitle());
        assertEquals(null, category.getCategoryDescription());
        Category category = new Category(1,"IT","Corporate");
        Quiz quiz1 = new Quiz(1L, "Quiz 1", "Description 1", 30,category);
        Quiz quiz2 = new Quiz(2L, "Quiz 2", "Description 2", 45,category);
        List<Quiz> quizList = new ArrayList<>();
        quizList.add(quiz1);
        quizList.add(quiz2);
        
        category.setCategoryId(1);
        category.setCategoryTitle("React");
        category.setCategoryDescription("React Description");
        category.setQuizzes(quizList);
        
        assertEquals(1, category.getCategoryId());
        assertEquals("React", category.getCategoryTitle());
        assertEquals("React Description", category.getCategoryDescription());
        assertEquals(quizList, category.getQuizzes());
    }
    @Test
    void testDefaultConstructor() {
        Category category = new Category();
        assertEquals(0,category.getCategoryId());
        assertEquals(null, category.getCategoryTitle());
        assertEquals(null, category.getCategoryDescription());
    }
    @Test
    void testParameterisedConstructor() {
        Category category = new Category(1,"React","React Description");
        assertEquals(1, category.getCategoryId());
        assertEquals("React", category.getCategoryTitle());
        assertEquals("React Description", category.getCategoryDescription());
    }

}
