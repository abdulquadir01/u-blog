package com.aq.blogapp.services;

import com.aq.blogapp.vo.DTO.CategoryDTO;

import java.util.List;


public interface CategoryService {

    List<CategoryDTO> getAllCategory();

    CategoryDTO getCategoryById(Long id);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);

}
