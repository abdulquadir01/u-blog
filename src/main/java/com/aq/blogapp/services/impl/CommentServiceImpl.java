package com.aq.blogapp.services.impl;

import com.aq.blogapp.model.User;
import com.aq.blogapp.payload.DTO.CommentDTO;
import com.aq.blogapp.exceptions.ResourceNotFoundException;
import com.aq.blogapp.utils.AppUtils;
import com.aq.blogapp.utils.mappers.CommentMapper;
import com.aq.blogapp.model.Blog;
import com.aq.blogapp.model.Comment;
import com.aq.blogapp.respositories.BlogRepository;
import com.aq.blogapp.respositories.CommentRepository;
import com.aq.blogapp.services.CommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;





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
    public List<CommentDTO> getAllComments() {

        return commentRepository
                    .findAll()
                    .stream()
                    .map(commentMapper::commentToCommentDto)
                    .collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long id) {

        return commentRepository
                .findById(id)
                .map(commentMapper::commentToCommentDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<CommentDTO> getCommentByUser(User user) {
        return commentRepository
                .findAllByUser(user)
                .stream()
                .map(commentMapper::commentToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> getCommentByBlog(Blog blog) {
        return commentRepository
                .findAllByBlog(blog)
                .stream()
                .map(commentMapper::commentToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> getCommentByUserOnBlog(User user, Blog blog) {
        return commentRepository
                .findAllByUserAndBlog(user, blog)
                .stream()
                .map(commentMapper::commentToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        CommentDTO updatedComment = new CommentDTO();
        Comment existingComment = new Comment();

        try{
            existingComment = commentRepository
                    .findById(id)
                    .orElseThrow( ()-> new ResourceNotFoundException("Comment", "id", id));

            existingComment.setComment(commentDTO.getComment());

            updatedComment = commentMapper.commentToCommentDto(commentRepository.save(existingComment));

        }catch (ResourceNotFoundException ex){
            ex.getMessage();
        }

        return updatedComment;
    }

    @Override
    public CommentDTO createComment(Long blogId, CommentDTO commentDTO) {

        Comment comment = commentMapper.commentDtoToComment(commentDTO);

        Blog blog = blogRepository
                .findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "blogId", blogId));

        comment.setBlog(blog);
        comment.setDate(AppUtils.dateFormatter(LocalDateTime.now()));

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
