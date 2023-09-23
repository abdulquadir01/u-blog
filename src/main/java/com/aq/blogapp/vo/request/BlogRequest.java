package com.aq.blogapp.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
@Builder
public class BlogRequest {

    @NotEmpty
    @Size(min=4, message = "Blog title must be at least 4 characters")
    private String title;

    @NotEmpty
    @Size(min=10, message = "Blog contents must be at least 500 characters")
    private String content;

}
