package com.aq.blogapp.controllers;

import com.aq.blogapp.DTO.BlogDTO;
import com.aq.blogapp.constants.AppConstants;
import com.aq.blogapp.exceptions.ResourceNotFoundException;
import com.aq.blogapp.payload.response.ApiResponse;
import com.aq.blogapp.payload.response.BlogResponse;
import com.aq.blogapp.services.BlogService;
import com.aq.blogapp.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class BlogController {

    private final BlogService blogService;
    private final FileService fileService;

    @Value("${project.image_path}")
    private String imagePath;

    public BlogController(BlogService blogService, FileService fileService) {
        this.blogService = blogService;
        this.fileService = fileService;
    }


    @GetMapping("/blogs")
    public ResponseEntity<Object> getAllBlogs(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        BlogResponse blogResponse = new BlogResponse();
        System.out.println("sortdir :" + sortDir);

        try {
            blogResponse = blogService.getAllBlog(pageNumber, pageSize, sortBy, sortDir);

            return new ResponseEntity<>(blogResponse, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(
                    new ApiResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }


    @GetMapping("/blogs/{blogId}")
    public ResponseEntity<Object> getBlogById(@PathVariable Long blogId) {
        BlogDTO blogDTOById = new BlogDTO();

        try {
            blogDTOById = blogService.getBlogById(blogId);

            return new ResponseEntity<>(blogDTOById, HttpStatus.OK);

        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(
                    new ApiResponse(
                            HttpStatus.NOT_FOUND.getReasonPhrase(),
                            HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND
            );
        }

    }


    @GetMapping("/category/{categoryId}/blogs")
    public ResponseEntity<Object> getBlogByCategory(
            @PathVariable Long categoryId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        BlogResponse blogsByCategoryResponse = new BlogResponse();

        try {
            blogsByCategoryResponse = blogService.getBlogsByCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);

            return new ResponseEntity<>(blogsByCategoryResponse, HttpStatus.OK);

        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(
                    new ApiResponse(
                            HttpStatus.NOT_FOUND.getReasonPhrase(),
                            HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND
            );
        }

    }


    @GetMapping("/user/{userId}/blogs")
    public ResponseEntity<Object> getBlogByUser(
            @PathVariable Long userId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        BlogResponse blogsByUser = new BlogResponse();

        try {
            blogsByUser = blogService.getBlogsByUser(userId, pageNumber, pageSize, sortBy, sortDir);

            return new ResponseEntity<>(blogsByUser, HttpStatus.OK);

        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(
                    new ApiResponse(
                            HttpStatus.NOT_FOUND.getReasonPhrase(),
                            HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND
            );
        }

    }


    @PostMapping("/user/{userId}/category/{categoryId}/blogs")
    public ResponseEntity<Object> createBlog(@Valid @RequestBody BlogDTO blogDTO,
                                             @PathVariable Long userId, @PathVariable Long categoryId) {
        BlogDTO newBlogDTO = new BlogDTO();

        try {
//            if(!AppUtils.anyEmpty(blogDTO)){
            if (true) {
                newBlogDTO = blogService.createBlog(blogDTO, userId, categoryId);

                return new ResponseEntity<>(newBlogDTO, HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>(
                        new ApiResponse(
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                HttpStatus.BAD_REQUEST.value()),
                        HttpStatus.BAD_REQUEST
                );
            }

        } catch (Exception ex) {
            return new ResponseEntity<>(
                    new ApiResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    @PutMapping("/blogs/{blogId}")
    public ResponseEntity<Object> updateBlog(@PathVariable Long blogId, @Valid @RequestBody BlogDTO blogDTO) {
        BlogDTO updatedBlog = new BlogDTO();

        try {
            updatedBlog = blogService.updateBlog(blogId, blogDTO);

            return new ResponseEntity<>(updatedBlog, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(
                    new ApiResponse(
                            "Internal Server Error",
                            HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }


    @DeleteMapping("/blogs/{blogId}")
    public ResponseEntity<Object> deleteBlog(@PathVariable Long blogId) {

        try {
            blogService.deleteBlog(blogId);

            return new ResponseEntity<>(
                    new ApiResponse(
                            "Category Deleted Successfully",
                            HttpStatus.OK.value()),
                    HttpStatus.OK
            );

        } catch (Exception ex) {
            return new ResponseEntity<>(
                    new ApiResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    //SEARCH METHOD
    @GetMapping("/blogs/search/{keyword}")
    public ResponseEntity<Object> searchBlogs(@PathVariable String keyword) {
        List<BlogDTO> searchedBlogs = new ArrayList<>();

        searchedBlogs = blogService.searchByTitle(keyword);

        return new ResponseEntity<>(searchedBlogs, HttpStatus.OK);
    }


    @PostMapping("/blogs/{blogId}/images/upload")
    public ResponseEntity<BlogDTO> uploadImg(@RequestParam("image") MultipartFile imageFile,
                                             @PathVariable Long blogId) throws IOException {

        BlogDTO blogDTO = blogService.getBlogById(blogId);

        System.out.println("uploadImg() k andar aa gaye");
        String imageName = fileService.uploadImg(imagePath, imageFile);

        blogDTO.setImageName(imageName);
        System.out.println("imageName set ho gaya");

        BlogDTO updatedBlog = blogService.updateBlog(blogId, blogDTO);
        System.out.println("image save hoke naya blog bann gaya");

        return new ResponseEntity<>(updatedBlog, HttpStatus.OK);

    }


    @GetMapping(value = "/blogs/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImg(@PathVariable String imageName, HttpServletResponse response) throws IOException {

        System.out.println("inside downloadImg method: " + imageName);
        System.out.println("video file path: " + imageName);

        InputStream resource = fileService.getBlogImage(imagePath, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }


//EoC - End of Class
}
