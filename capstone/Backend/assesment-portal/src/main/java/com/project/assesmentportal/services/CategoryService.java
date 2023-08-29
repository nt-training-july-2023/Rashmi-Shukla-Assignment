package com.project.assesmentportal.services;

import java.util.List;

import com.project.assesmentportal.dto.CategoryDto;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(long categoryId);

    CategoryDto updateCategory(CategoryDto categoryDto, long categoryId);

    void deleteCategory(long categoryId);
}
