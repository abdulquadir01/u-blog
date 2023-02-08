package com.aq.blogapp.payload.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {

    private Long blogId;

    //    @NotEmpty
//    @Size(min=4, message = "Blog title must be at least 4 characters")
    private String title;

    //    @NotEmpty
//    @Size(min=10, message = "Blog contents must be at least 500 characters")
    private String content;

    //    @NotEmpty
//    @Size(min=4, message = "Blog imageName must be at least 4 characters")
    private String imageName;

    //    @NotEmpty
//    @Size(min=4, message = "Provide date of blogging")
    private String bloggedDate;


    private CategoryDTO category;

    private UserDTO user;

    private Set<CommentDTO> comments = new LinkedHashSet<>();


}
