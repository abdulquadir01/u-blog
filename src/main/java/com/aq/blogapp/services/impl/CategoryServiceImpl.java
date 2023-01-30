package com.aq.blogapp.services.impl;

import com.aq.blogapp.DTO.CategoryDTO;
import com.aq.blogapp.exceptions.ResourceNotFoundException;
import com.aq.blogapp.mappers.CategoryMapper;
import com.aq.blogapp.model.Category;
import com.aq.blogapp.respositories.CategoryRepository;
import com.aq.blogapp.services.CategoryService;
import com.aq.blogapp.utils.AppUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<CategoryDTO> getAllCategory() {

        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        categoryDTOList = categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());

        return categoryDTOList;
    }


    @Override
    public CategoryDTO getCategoryById(Long id) {
        CategoryDTO categoryDtoById = new CategoryDTO();

        try {
            if (id != null) {
                categoryDtoById = categoryRepository
                        .findById(id)
                        .map(categoryMapper::categoryToCategoryDto)
                        .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", id));
            }
        } catch (NoSuchElementException ex) {
            throw new ResourceNotFoundException("Category", "CategoryId", id);
        }

        return categoryDtoById;
    }


    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        CategoryDTO newCategoryDTO = new CategoryDTO();

        try {
            if (!AppUtils.anyEmpty(categoryDTO)) {
                newCategoryDTO = saveAndReturnDTO(categoryMapper.categoryDtoToCategory(categoryDTO));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getCause());

        }

        return newCategoryDTO;
    }


    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = new CategoryDTO();
        Category existingCategory = new Category();

        try {
            existingCategory = categoryRepository
                    .findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", id));

            existingCategory.setCategoryTitle(categoryDTO.getCategoryTitle());
            existingCategory.setCategoryDescription(categoryDTO.getCategoryDescription());

            updatedCategory = categoryMapper.categoryToCategoryDto(categoryRepository.save(existingCategory));

        } catch (ResourceNotFoundException RNFE) {
            RNFE.getMessage();
        }

        return updatedCategory;
    }


    @Override
    public void deleteCategory(Long id) {

        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ERDAE) {
            System.out.println(ERDAE.getMessage());
            System.out.println(ERDAE.getCause());
            throw new ResourceNotFoundException("Category", "CategoryId", id);
        }

    }


    //  PRIVATE methods
    private CategoryDTO saveAndReturnDTO(Category category) {
        CategoryDTO returnedDto = new CategoryDTO();

        if (category != null) {
            Category savedCategory = categoryRepository.save(category);
            returnedDto = categoryMapper.categoryToCategoryDto(savedCategory);
        }

        return returnedDto;
    }

}
