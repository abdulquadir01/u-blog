package com.aq.blogapp.payload.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;




@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponse {

    private Long blogId;

    private String title;

    private String content;

    private String imageName;

    private String bloggedDate;

    private CategoryResponse category;

    private UserResponse user;

    private Set<CommentResponse> comments = new LinkedHashSet<>();


}
