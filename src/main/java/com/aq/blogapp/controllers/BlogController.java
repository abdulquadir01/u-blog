package com.aq.blogapp.controllers;


import com.aq.blogapp.DTO.BlogDTO;
import com.aq.blogapp.DTO.CategoryDTO;
import com.aq.blogapp.exceptions.ResourceNotFoundException;
import com.aq.blogapp.mappers.BlogMapper;
import com.aq.blogapp.model.Blog;
import com.aq.blogapp.services.BlogService;
import com.aq.blogapp.utils.ApiResponse;
import com.aq.blogapp.utils.AppUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class BlogController {

    private final BlogService blogService;

    private  final BlogMapper blogMapper;

    public BlogController(BlogService blogService, BlogMapper blogMapper) {
        this.blogService = blogService;
        this.blogMapper = blogMapper;
    }


    @GetMapping("/blogs")
    public ResponseEntity<Object> getAllBlogs() {
        List<BlogDTO> allBlogs = new ArrayList<>();

        try{
            allBlogs = blogService
                            .getAllBlog()
                            .stream()
                            .map(blog -> blogMapper.blogToBlogDto(blog))
                            .collect(Collectors.toList());
            return new ResponseEntity<>(allBlogs, HttpStatus.OK);

        }catch (Exception ex){
            return new ResponseEntity<>(
                    new ApiResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }


    @GetMapping("/blogs/{blogId}")
    public ResponseEntity<Object> getBlogById(@PathVariable Long blogId){
        BlogDTO blogDTOById = new BlogDTO();

        try{
            blogDTOById = blogService.getBlogById(blogId);
//            System.out.println(blog.toString());
            return new ResponseEntity<>( blogDTOById, HttpStatus.OK);

        } catch (ResourceNotFoundException ex){
            return  new ResponseEntity<>(
                    new ApiResponse(
                            HttpStatus.NOT_FOUND.getReasonPhrase(),
                            HttpStatus.NOT_FOUND.value() ),
                    HttpStatus.NOT_FOUND
            );
        }

    }


    @GetMapping("/category/{categoryId}/blogs")
    public ResponseEntity<Object> getBlogByCategory(@PathVariable Long categoryId){
        List<BlogDTO> allBlogsByCategory = new ArrayList<>();

        try{
            allBlogsByCategory = blogService.getBlogsByCategory(categoryId);
//            System.out.println(blog.toString());
            return new ResponseEntity<>( allBlogsByCategory, HttpStatus.OK);

        } catch (ResourceNotFoundException ex){
            return  new ResponseEntity<>(
                    new ApiResponse(
                            HttpStatus.NOT_FOUND.getReasonPhrase(),
                            HttpStatus.NOT_FOUND.value() ),
                    HttpStatus.NOT_FOUND
            );
        }

    }


    @GetMapping("/user/{userId}/blogs")
    public ResponseEntity<Object> getBlogByUser(@PathVariable Long userId){
        List<BlogDTO> allBlogsByUser = new ArrayList<>();

        try{
            allBlogsByUser = blogService.getBlogsByUser(userId);
            return new ResponseEntity<>( allBlogsByUser, HttpStatus.OK);

        } catch (ResourceNotFoundException ex){
            return  new ResponseEntity<>(
                    new ApiResponse(
                            HttpStatus.NOT_FOUND.getReasonPhrase(),
                            HttpStatus.NOT_FOUND.value() ),
                    HttpStatus.NOT_FOUND
            );
        }

    }



    @PostMapping("/user/{userId}/category/{categoryId}/blogs")
    public ResponseEntity<Object> createBlog(@Valid @RequestBody BlogDTO blogDTO,
                                             @PathVariable Long userId, @PathVariable Long categoryId){
        BlogDTO newBlogDTO = new BlogDTO();

        try{
//            if(!AppUtils.anyEmpty(blogDTO)){
            if(true){
//                System.out.println("blogs create krne wala hai");
                newBlogDTO = blogService.createBlog(blogDTO, userId, categoryId);
//                System.out.println("blogs create kr liya hai");

                return  new ResponseEntity<>(newBlogDTO, HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(
                        new ApiResponse(
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                HttpStatus.BAD_REQUEST.value()),
                        HttpStatus.BAD_REQUEST
                );
            }

        }catch (Exception ex){
             return new  ResponseEntity<>(
                    new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR
             );
        }
    }


    @PutMapping("/blogs/{blogId}")
    public ResponseEntity<Object> updateBlog(@PathVariable Long blogId,@Valid @RequestBody BlogDTO blogDTO){
        BlogDTO updatedBlog = new BlogDTO();

        try{

            updatedBlog = blogService.updateBlog(blogId, blogDTO);
            return new ResponseEntity<>( updatedBlog, HttpStatus.OK);

        }
        catch (Exception ex){
            return new ResponseEntity<>(
                    new ApiResponse(
                            String.format("Internal Server Error"),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR
                    );
        }

    }


    @DeleteMapping("/blogs/{blogId}")
    public ResponseEntity<Object> deleteBlog(@PathVariable Long blogId){

        try{
            blogService.deleteBlog(blogId);
             return new ResponseEntity<>(
                     new ApiResponse(
                             String.format("Category Deleted Successfully"),
                             HttpStatus.OK.value()),
                     HttpStatus.OK
             );

        }catch (Exception ex){
            return new ResponseEntity<>(
                    new ApiResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}
