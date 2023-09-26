package com.aq.blogapp.vo.response;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentResponse {

    private Long commentId;

    private String comment;


}
