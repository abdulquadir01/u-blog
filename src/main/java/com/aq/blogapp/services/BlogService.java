package com.aq.blogapp.services;

import com.aq.blogapp.DTO.BlogDTO;
import com.aq.blogapp.payload.BlogResponse;

import java.util.List;


public interface BlogService {

    BlogResponse getAllBlog(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    BlogDTO getBlogById(Long id);

    BlogDTO createBlog(BlogDTO blogDTO, Long userId, Long categoryId);

    BlogDTO updateBlog(Long id, BlogDTO blogDTO);

    void deleteBlog(Long id);

    BlogResponse getBlogsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    BlogResponse getBlogsByUser(Long userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //    search for posts
    List<BlogDTO> searchByTitle(String keywords);

}
