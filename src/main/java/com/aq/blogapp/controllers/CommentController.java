package com.aq.blogapp.controllers;


import com.aq.blogapp.model.Blog;
import com.aq.blogapp.model.User;
import com.aq.blogapp.payload.DTO.CommentDTO;
import com.aq.blogapp.payload.response.ApiResponse;
import com.aq.blogapp.services.BlogService;
import com.aq.blogapp.services.CommentService;
import com.aq.blogapp.services.UserService;
import com.aq.blogapp.utils.mappers.BlogMapper;
import com.aq.blogapp.utils.mappers.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    private final UserService userService;

    private final BlogService blogService;

    public CommentController(CommentService commentService, UserService userService, BlogService blogService) {
        this.commentService = commentService;
        this.userService = userService;
        this.blogService = blogService;
    }

    @GetMapping("/comments")
    public ResponseEntity<Object> getAllComments(){
        List<CommentDTO> comments = commentService.getAllComments();

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }


    @GetMapping("/comments/{id}")
    public ResponseEntity<Object> getCommentById(@PathVariable("id") Long  commentId){
        CommentDTO commentDTO = commentService.getCommentById(commentId);

        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }


    @PostMapping("/blogs/{blogId}/comments")
    public ResponseEntity<Object> createComment(@RequestBody CommentDTO commentDTO, @PathVariable Long blogId) {

        CommentDTO createdComment = commentService.createComment(blogId, commentDTO);

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);

    }


    @GetMapping("/users/{userId}/comments")
    public ResponseEntity<Object> getCommentByUser(@PathVariable("userId") Long id){

        User user = UserMapper.INSTANCE.userDtoToUser(userService.getUserById(id));
        List<CommentDTO> commentsByUser = commentService.getCommentByUser(user);

        return new ResponseEntity<>(commentsByUser, HttpStatus.OK);
    }


    @GetMapping("/blogs/{blogId}/comments")
    public ResponseEntity<Object> getCommentByBlog(@PathVariable("blogId") Long id){

        Blog blog = BlogMapper.INSTANCE.blogDtoToBlog(blogService.getBlogById(id));
        List<CommentDTO> commentsByUser = commentService.getCommentByBlog(blog);

        return new ResponseEntity<>(commentsByUser, HttpStatus.OK);
    }

    @GetMapping("/blogs/{blogId}/users/{userId}/comments")
    public ResponseEntity<Object> getCommentByUserOnBlog(@PathVariable Long blogId, @PathVariable Long userId){

        Blog blog = BlogMapper.INSTANCE.blogDtoToBlog(blogService.getBlogById(blogId));
        User user = UserMapper.INSTANCE.userDtoToUser(userService.getUserById(blogId));

        List<CommentDTO> comments = commentService.getCommentByUserOnBlog(user, blog);

        return new ResponseEntity<>(comments, HttpStatus.OK);

    }


    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Object> deleteComment(@PathVariable Long commentId) {

        commentService.deleteComment(commentId);

        return new ResponseEntity<>(
                new ApiResponse("Comment Deleted Successfully!!", HttpStatus.URI_TOO_LONG.value()),
                HttpStatus.OK
        );

    }


}
