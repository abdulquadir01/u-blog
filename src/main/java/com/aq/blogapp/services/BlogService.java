package com.aq.blogapp.services;

import com.aq.blogapp.DTO.BlogDTO;
import com.aq.blogapp.payload.BlogResponse;

import java.util.List;


public interface BlogService {

    BlogResponse getAllBlog(Integer pageNumber, Integer pageSize);

    BlogDTO getBlogById(Long id);

    BlogDTO createBlog(BlogDTO blogDTO, Long userId, Long categoryId);

    BlogDTO updateBlog(Long id, BlogDTO blogDTO);

    void deleteBlog(Long id);

    BlogResponse getBlogsByCategory(Long categoryId, Integer pageNumber, Integer pageSize);

    BlogResponse getBlogsByUser(Long userId, Integer pageNumber, Integer pageSize);

    //    search for posts
    List<BlogDTO> searchBlogs(String keywords);

}
