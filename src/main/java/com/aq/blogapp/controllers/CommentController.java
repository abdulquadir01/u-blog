package com.aq.blogapp.controllers;


import com.aq.blogapp.payload.DTO.CommentDTO;
import com.aq.blogapp.payload.response.ApiResponse;
import com.aq.blogapp.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CommentController {

    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("/blogs/{blogId}/comments")
    public ResponseEntity<Object> createComment(@RequestBody CommentDTO commentDTO, @PathVariable Long blogId) {

        CommentDTO createdComment = commentService.createComment(blogId, commentDTO);

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);

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
