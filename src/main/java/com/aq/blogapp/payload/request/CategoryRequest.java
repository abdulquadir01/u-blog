package com.aq.blogapp.payload.request;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CategoryRequest {

    @NotEmpty
    @Size(min = 4, message = "Category title must be at least 4 characters")
    private String categoryTitle;

    @NotEmpty
    @Size(min = 25, message = "Category description must be at least 25 characters")
    private String categoryDescription;


}
