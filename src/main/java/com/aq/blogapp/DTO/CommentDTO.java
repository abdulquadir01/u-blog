package com.aq.blogapp.DTO;


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


}
