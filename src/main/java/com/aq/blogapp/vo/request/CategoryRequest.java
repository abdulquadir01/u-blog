package com.aq.blogapp.vo.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class CategoryRequest {

    @NotEmpty
    @Size(min = 4, message = "Category title must be at least 4 characters")
    private String categoryTitle;

    @NotEmpty
    @Size(min = 25, message = "Category description must be at least 25 characters")
    private String categoryDescription;


}
