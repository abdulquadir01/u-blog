package com.aq.blogapp.payload.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommentResponse {

    private Long commentId;

    private String comment;


}
