package com.aq.blogapp.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long categoryId;

    @NotEmpty
    @Size(min = 4, message = "Category title must be at least 4 characters")
    private String categoryTitle;

    @NotEmpty
    @Size(min = 25, message = "Category description must be at least 25 characters")
    private String categoryDescription;
}
