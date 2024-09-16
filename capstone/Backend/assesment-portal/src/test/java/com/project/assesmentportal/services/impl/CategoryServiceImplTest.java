package com.project.assesmentportal.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import com.project.assesmentportal.dto.ApiResponse;
import com.project.assesmentportal.dto.CategoryDto;
import com.project.assesmentportal.dto.QuizDto;
import com.project.assesmentportal.entities.Category;
import com.project.assesmentportal.entities.Quiz;
import com.project.assesmentportal.exceptions.DuplicateResourceException;
import com.project.assesmentportal.exceptions.ResourceNotFoundException;
import com.project.assesmentportal.messages.MessageConstants;
import com.project.assesmentportal.repositories.CategoryRepository;

class CategoryServiceImplTest {
    @InjectMocks
    private CategoryServiceImpl categoryServiceImpl;

    @Mock
    private CategoryRepository categoryRepository;
    
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testAddCategory_Success() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(1);
        categoryDto.setCategoryTitle("React");
        categoryDto.setCategoryDescription("Mcqs");
        
        Category category = new Category();
        category.setCategoryId(categoryDto.getCategoryId());
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        
        when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
        when(categoryRepository.findByCategoryTitle(category.getCategoryTitle())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        
        ApiResponse expectedResponse = new ApiResponse(
                MessageConstants.CATEGORY_ADDED_SUCCESSFULLY,
                HttpStatus.CREATED.value()
                );
        
        ApiResponse result = categoryServiceImpl.addCategory(categoryDto);
        assertNotNull(result);
        assertEquals(result, expectedResponse);
    }

    @Test
    void testAddCategory_DuplicateResourceException() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(1);
        categoryDto.setCategoryTitle("React");
        categoryDto.setCategoryDescription("Mcqs");
        
        Category category = new Category();
        category.setCategoryId(categoryDto.getCategoryId());
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        
        when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
        when(categoryRepository.findByCategoryTitle(category.getCategoryTitle())).thenReturn(Optional.of(category));
        
        assertThrows(DuplicateResourceException.class, () -> {
            categoryServiceImpl.addCategory(categoryDto);
        });
        
    }
    
    @Test
    void testGetCategoryById_Success() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryTitle("React");
        category.setCategoryDescription("Mcqs");
        
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setCategoryTitle(category.getCategoryTitle());
        categoryDto.setCategoryDescription(category.getCategoryDescription());
        
        when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);
        when(categoryRepository.findById(category.getCategoryId())).thenReturn(Optional.of(category));
        
        CategoryDto resultCategoryDto = categoryServiceImpl.getCategoryById(1);
        assertNotNull(resultCategoryDto);
        assertEquals(resultCategoryDto,categoryDto);
    }
    
    @Test
    void testGetCategoryById_CategoryNotFound() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryTitle("React");
        category.setCategoryDescription("Mcqs");
        
        when(categoryRepository.findById(category.getCategoryId())).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            categoryServiceImpl.getCategoryById(1);
        });
        
    }
    
    @Test
    public void testGetCategories() {
        Category cat1 = new Category(1,"React","Mcq");
        Category cat2 = new Category(2,"Java","Mcq");
        List<Category> catList = new ArrayList<>();
        catList.add(cat1);
        catList.add(cat2);
        
        CategoryDto catDto1 = new CategoryDto(1,"React","Mcq");
        CategoryDto catDto2 = new CategoryDto(2,"Java","Mcq");
        List<CategoryDto> catDtos = new ArrayList<>();
        catDtos.add(catDto1);
        catDtos.add(catDto2);
        
        when(modelMapper.map(cat1, CategoryDto.class)).thenReturn(catDto1);
        when(modelMapper.map(cat2, CategoryDto.class)).thenReturn(catDto2);
        when(categoryRepository.findAll()).thenReturn(catList);
        
        List<CategoryDto> response = categoryServiceImpl.getCategories();
        System.out.println(response);

        assertNotNull(response);
        assertEquals(catDtos,response);
    }
    
    @Test
    void testUpdateCategory_Success() {
        long categoryIdToUpdate = 1L;
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryTitle("React");
        categoryDto.setCategoryDescription("Mcqs");
        
        Category existingCategory = new Category();
        existingCategory.setCategoryId(categoryIdToUpdate);
        existingCategory.setCategoryTitle("Java");
        existingCategory.setCategoryDescription("mcqs");
        
        when(modelMapper.map(existingCategory, CategoryDto.class)).thenReturn(categoryDto);
        when(categoryRepository.findById(categoryIdToUpdate)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.findByCategoryTitle(categoryDto.getCategoryTitle())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);
        
        ApiResponse expectedResponse = new ApiResponse(
                MessageConstants.CATEGORY_UPDATED_SUCCESSFULLY,
                HttpStatus.OK.value()
                );
        
        ApiResponse result = categoryServiceImpl.updateCategory(categoryDto,categoryIdToUpdate);
        assertNotNull(result);
        assertEquals(result, expectedResponse);
    }
    
    @Test
    void testUpdateCategory_ResourceNotFound() {
        long categoryIdToUpdate = 1L;
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryTitle("React");
        categoryDto.setCategoryDescription("Mcqs");
        
        Category existingCategory = new Category();
        existingCategory.setCategoryId(categoryIdToUpdate);
        existingCategory.setCategoryTitle("Java");
        existingCategory.setCategoryDescription("mcqs");
        
        when(categoryRepository.findById(categoryIdToUpdate)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            categoryServiceImpl.updateCategory(categoryDto, 1);
        });
        
    }
    
    @Test
    void testUpdateCategory_DuplicateEmail() {
        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryTitle("Java");
        
        long categoryIdToUpdate = 1L;
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryTitle("React");
        categoryDto.setCategoryDescription("Mcqs");
        
        Category existingCategory = new Category();
        existingCategory.setCategoryId(2L);
        existingCategory.setCategoryTitle("React");
        existingCategory.setCategoryDescription("mcqs");
        
        when(categoryRepository.findById(categoryIdToUpdate)).thenReturn(Optional.of(category));
        when(categoryRepository.findByCategoryTitle(categoryDto.getCategoryTitle())).thenReturn(Optional.of(existingCategory));

        
        assertThrows(DuplicateResourceException.class, () -> {
            categoryServiceImpl.updateCategory(categoryDto, 1);
        });
        
    }
    
    @Test
    public final void testDeleteCategory_Success() {
        long categoryIdToDelete = 1L;
        Category categoryToDelete = new Category();
        categoryToDelete.setCategoryId(categoryIdToDelete);

        when(categoryRepository.findById(categoryIdToDelete)).thenReturn(Optional.of(categoryToDelete));

        ApiResponse expectedResponse = new ApiResponse(
                MessageConstants.CATEGORY_DELETED_SUCCESSFULLY,
                HttpStatus.OK.value()
                );

        ApiResponse response = categoryServiceImpl.deleteCategory(categoryIdToDelete);

        verify(categoryRepository, times(1)).deleteById(categoryIdToDelete);
        assertEquals(response, expectedResponse);
    }
    
    @Test
    public final void testDeleteCategory_NotFound() {
        long categoryIdToDelete = 1L;

        when(categoryRepository.findById(categoryIdToDelete)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            categoryServiceImpl.deleteCategory(categoryIdToDelete);
        });
    }

    @Test
    public final void testGetQuizzesByCategory_Success() {
        long catId = 1L;
        Category category = new Category();
        category.setCategoryId(catId);
        category.setCategoryTitle("React");
        category.setCategoryDescription("Mcqs");
        Quiz quiz = new Quiz(1, "React", "descr", 20, category);
        List<Quiz> quizList = new ArrayList<>();
        quizList.add(quiz);
        category.setQuizzes(quizList);
        
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setCategoryTitle(category.getCategoryTitle());
        categoryDto.setCategoryDescription(category.getCategoryDescription());
        QuizDto quizDto = new QuizDto(1, "React", "descr", 20, categoryDto);
        List<QuizDto> quizDtosList = new ArrayList<>();
        quizDtosList.add(quizDto);
        
        when(categoryRepository.findById(catId)).thenReturn(Optional.of(category));
        when(modelMapper.map(quiz, QuizDto.class)).thenReturn(quizDto);
        when(modelMapper.map(quiz.getCategory(), CategoryDto.class)).thenReturn(categoryDto);
        
        List<QuizDto> response = categoryServiceImpl.getQuizzesByCategory(catId);
        
        assertNotNull(response);
        assertEquals(quizDtosList, response);
    }
    
    @Test
    public final void testGetQuizzesByCategory_CategoryNotFound() {
        long catId = 1L;
        Category category = new Category();
        category.setCategoryId(catId);
        category.setCategoryTitle("React");
        category.setCategoryDescription("Mcqs");
        Quiz quiz = new Quiz(1, "React", "descr", 20, category);
        List<Quiz> quizList = new ArrayList<>();
        quizList.add(quiz);
        category.setQuizzes(quizList);
        
        when(categoryRepository.findById(catId)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            categoryServiceImpl.getQuizzesByCategory(catId);
        });
    }

}
