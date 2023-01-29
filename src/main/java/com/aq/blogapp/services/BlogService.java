package com.aq.blogapp.services;

import com.aq.blogapp.DTO.BlogDTO;
import com.aq.blogapp.model.Blog;

import java.util.List;




public interface BlogService {

    List<Blog> getAllBlog();

    BlogDTO getBlogById(Long id);

    BlogDTO createBlog(BlogDTO blogDTO, Long userId, Long categoryId);

    BlogDTO updateBlog(Long id, BlogDTO blogDTO);

    void deleteBlog(Long id);

    List<BlogDTO> getBlogsByCategory(Long categoryId);

    List<BlogDTO> getBlogsByUser(Long userId);

//    search for posts
    List<BlogDTO> searchBlogs(String keywords);

}