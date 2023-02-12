package com.aq.blogapp.payload.request;

import lombok.Data;



@Data
public class BlogRequest {

//    @NotEmpty
//    @Size(min=4, message = "Blog title must be at least 4 characters")
    private String title;

//    @NotEmpty
//    @Size(min=10, message = "Blog contents must be at least 500 characters")
    private String content;

}
