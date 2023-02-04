package com.aq.blogapp.controllers;


import com.aq.blogapp.DTO.CategoryDTO;
import com.aq.blogapp.exceptions.ResourceNotFoundException;
import com.aq.blogapp.payload.response.ApiResponse;
import com.aq.blogapp.services.CategoryService;
import com.aq.blogapp.utils.AppUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping
    public ResponseEntity<Object> getAllCategory() {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        try {
            categoryDTOList = categoryService.getAllCategory();

            return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);

        } catch (Exception ex) {

            return new ResponseEntity<>(
                    new ApiResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }


    @GetMapping("/{categoryId}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Long categoryId) {
        CategoryDTO categoryDtoById = new CategoryDTO();

        try {
            categoryDtoById = categoryService.getCategoryById(categoryId);
            System.out.println(categoryDtoById.toString());

            return new ResponseEntity<>(categoryDtoById, HttpStatus.OK);

        } catch (ResourceNotFoundException ex) {

            return new ResponseEntity<>(
                    new ApiResponse(
                            HttpStatus.NOT_FOUND.getReasonPhrase(),
                            HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND
            );
        }

    }


    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategoryDto = new CategoryDTO();

        try {
            if (!AppUtils.anyEmpty(categoryDTO)) {
                createdCategoryDto = categoryService.createCategory(categoryDTO);

                return new ResponseEntity<>(createdCategoryDto, HttpStatus.CREATED);
            } else {

                return new ResponseEntity<>(
                        new ApiResponse(
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                HttpStatus.BAD_REQUEST.value()),
                        HttpStatus.BAD_REQUEST
                );
            }

        } catch (Exception ex) {

            return new ResponseEntity<>(
                    new ApiResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    @PutMapping("/{categoryId}")
    public ResponseEntity<Object> updateUser(@PathVariable Long categoryId, @Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = new CategoryDTO();

        try {
            updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);

            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);

        } catch (Exception ex) {

            return new ResponseEntity<>(
                    new ApiResponse(
                            "Internal Server Error",
                            HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }


    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long categoryId) {

        try {
            categoryService.deleteCategory(categoryId);

            return new ResponseEntity<>(
                    new ApiResponse(
                            "Category Deleted Successfully",
                            HttpStatus.OK.value()),
                    HttpStatus.OK
            );

        } catch (Exception ex) {

            return new ResponseEntity<>(
                    new ApiResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}
