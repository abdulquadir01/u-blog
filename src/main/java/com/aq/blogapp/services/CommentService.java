package com.aq.blogapp.services;

import com.aq.blogapp.model.Blog;
import com.aq.blogapp.model.User;
import com.aq.blogapp.vo.DTO.CommentDTO;

import java.util.List;





public interface CommentService {

    List<CommentDTO> getAllComments();

    CommentDTO getCommentById(Long id);

    List<CommentDTO> getCommentByUser(User user);

    List<CommentDTO> getCommentByBlog(Blog blog);

    List<CommentDTO> getCommentByUserOnBlog(User user, Blog blog);

    CommentDTO updateComment(Long id, CommentDTO commentDTO);

    CommentDTO createComment(Long blogId, CommentDTO commentDTO);

    void deleteComment(Long commentId);

}
