package com.aq.blogapp.payload.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long categoryId;

    private String categoryTitle;

    private String categoryDescription;
}
