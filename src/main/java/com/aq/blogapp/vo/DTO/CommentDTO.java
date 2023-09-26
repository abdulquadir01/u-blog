package com.aq.blogapp.vo.DTO;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentDTO {

    private Long commentId;

    private String comment;

    private String date;

    private BlogDTO blog;


}
