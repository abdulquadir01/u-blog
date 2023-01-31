package com.aq.blogapp.services.impl;

import com.aq.blogapp.DTO.CommentDTO;
import com.aq.blogapp.exceptions.ResourceNotFoundException;
import com.aq.blogapp.mappers.CommentMapper;
import com.aq.blogapp.model.Blog;
import com.aq.blogapp.model.Comment;
import com.aq.blogapp.respositories.BlogRepository;
import com.aq.blogapp.respositories.CommentRepository;
import com.aq.blogapp.services.CommentService;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    public CommentServiceImpl(BlogRepository blogRepository, CommentRepository commentRepository, CommentMapper commentMapper) {
        this.blogRepository = blogRepository;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }


    @Override
    public CommentDTO createComment(Long blogId, CommentDTO commentDTO) {

        Comment comment = commentMapper.commentDtoToComment(commentDTO);

        Blog blog = blogRepository
                .findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "blogId", blogId));

        comment.setBlog(blog);

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.commentToCommentDto(savedComment);
    }

    @Override
    public void deleteComment(Long commentId) {

        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));

        commentRepository.delete(comment);

    }
}
