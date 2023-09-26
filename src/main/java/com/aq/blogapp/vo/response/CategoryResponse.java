package com.aq.blogapp.vo.response;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoryResponse {

    private Long categoryId;

    private String categoryTitle;

    private String categoryDescription;
}
