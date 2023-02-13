package com.aq.blogapp.payload.DTO;


import com.aq.blogapp.model.Blog;
import com.aq.blogapp.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommentDTO {

    private Long commentId;

    private String comment;

    private String date;

    private BlogDTO blog;


}
