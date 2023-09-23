package com.aq.blogapp.vo.DTO;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoryDTO {

    private Long categoryId;

    @NotEmpty
    @Size(min = 4, message = "Category title must be at least 4 characters")
    private String categoryTitle;

    @NotEmpty
    @Size(min = 25, message = "Category description must be at least 25 characters")
    private String categoryDescription;
}
