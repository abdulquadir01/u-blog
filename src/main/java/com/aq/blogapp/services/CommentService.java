package com.aq.blogapp.services;

import com.aq.blogapp.payload.DTO.CommentDTO;

public interface CommentService {

    CommentDTO createComment(Long blogId, CommentDTO commentDTO);

    void deleteComment(Long commentId);

}
