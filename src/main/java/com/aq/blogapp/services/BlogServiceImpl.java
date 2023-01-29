package com.aq.blogapp.services;

import com.aq.blogapp.DTO.BlogDTO;
import com.aq.blogapp.DTO.UserDTO;
import com.aq.blogapp.exceptions.ResourceNotFoundException;
import com.aq.blogapp.mappers.BlogMapper;
import com.aq.blogapp.mappers.UserMapper;
import com.aq.blogapp.model.Blog;
import com.aq.blogapp.model.Category;
import com.aq.blogapp.model.User;
import com.aq.blogapp.payload.BlogResponse;
import com.aq.blogapp.respositories.BlogRepository;
import com.aq.blogapp.respositories.CategoryRepository;
import com.aq.blogapp.respositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public BlogServiceImpl(BlogRepository blogRepository, BlogMapper blogMapper,
                           UserRepository userRepository, CategoryRepository categoryRepository) {
        this.blogRepository = blogRepository;
        this.blogMapper = blogMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public BlogResponse getAllBlog(Integer pageNumber, Integer pageSize) {

        List<BlogDTO> blogDTOList = new ArrayList<>();
        BlogResponse blogsByUserResponse = new BlogResponse();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Blog> blogPage = blogRepository.findAll(pageable);

        blogDTOList = blogPage.getContent()
                .stream()
                .map(blogMapper::blogToBlogDto)
                .collect(Collectors.toList());

        blogsByUserResponse.setBlogs(blogDTOList);
        blogsByUserResponse.setPageNumber(blogPage.getNumber());
        blogsByUserResponse.setPageSize(blogPage.getSize());
        blogsByUserResponse.setTotalPages(blogPage.getTotalPages());
        blogsByUserResponse.setTotalElements(blogPage.getTotalElements());
        blogsByUserResponse.setLastPage(blogPage.isLast());

        return blogsByUserResponse;
    }

    @Override
    public BlogDTO getBlogById(Long id) {
        BlogDTO blogDTO = new BlogDTO();

        try {
            if (id != null) {
                blogDTO = blogRepository
                        .findById(id)
                        .map(blogMapper::blogToBlogDto)
                        .orElseThrow(() -> new ResourceNotFoundException("Blog", "BlogId", id));
            }
        } catch (NoSuchElementException ex) {
            throw new ResourceNotFoundException("Blog", "BlogId", id);
        }

        return blogDTO;
    }

    @Override
    public BlogDTO createBlog(BlogDTO blogDTO, Long userId, Long categoryId) {
        BlogDTO newBlogDTO = new BlogDTO();
        Blog createdBlog = new Blog();
        Blog newBlog = new Blog();

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        newBlog = blogMapper.blogDtoToBlog(blogDTO);
        newBlog.setImageName("default.png");
        newBlog.setBloggedDate(new Date());
        newBlog.setUser(user);
        newBlog.setCategory(category);

        createdBlog = blogRepository.save(newBlog);

        newBlogDTO = blogMapper.blogToBlogDto(createdBlog);

        return newBlogDTO;
    }

    @Override
    public BlogDTO updateBlog(Long id, BlogDTO blogDTO) {
        BlogDTO updatedBlog = new BlogDTO();
        Blog savedBlog = new Blog();

        try {

            Blog exitingBlog = blogRepository
                    .findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Blog", "blogId", id));

            exitingBlog.setTitle(blogDTO.getTitle());
            exitingBlog.setContent(blogDTO.getContent());
            exitingBlog.setImageName(blogDTO.getImageName());

            savedBlog = blogRepository.save(exitingBlog);

            updatedBlog = blogMapper.blogToBlogDto(savedBlog);


        } catch (Exception ex) {
            throw new ResourceNotFoundException("Blog", "blogId", id);
        }

        return updatedBlog;
    }


    @Override
    public BlogResponse getBlogsByCategory(Long categoryId, Integer pageNumber, Integer pageSize) {
        List<BlogDTO> blogsByCategory = new ArrayList<>();
        BlogResponse blogsByUserResponse = new BlogResponse();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Page<Blog> blogPage = blogRepository.findAllByCategory(category, pageable);

        blogsByCategory = blogPage.getContent()
                .stream()
                .map(blogMapper::blogToBlogDto)
                .collect(Collectors.toList());

        blogsByUserResponse.setBlogs(blogsByCategory);
        blogsByUserResponse.setPageNumber(blogPage.getNumber());
        blogsByUserResponse.setPageSize(blogPage.getSize());
        blogsByUserResponse.setTotalPages(blogPage.getTotalPages());
        blogsByUserResponse.setTotalElements(blogPage.getTotalElements());
        blogsByUserResponse.setLastPage(blogPage.isLast());

        return blogsByUserResponse;
    }


    @Override
    public BlogResponse getBlogsByUser(Long userId, Integer pageNumber, Integer pageSize) {
        List<BlogDTO> blogsByUser = new ArrayList<>();
        BlogResponse blogsByUserResponse = new BlogResponse();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        Page<Blog> blogPage = blogRepository
                .findAllByUser(user, pageable);

        blogsByUser = blogPage
                .getContent()
                .stream()
                .map(blogMapper::blogToBlogDto)
                .collect(Collectors.toList());

        blogsByUserResponse.setBlogs(blogsByUser);
        blogsByUserResponse.setPageNumber(blogPage.getNumber());
        blogsByUserResponse.setPageSize(blogPage.getSize());
        blogsByUserResponse.setTotalPages(blogPage.getTotalPages());
        blogsByUserResponse.setTotalElements(blogPage.getTotalElements());
        blogsByUserResponse.setLastPage(blogPage.isLast());

        return blogsByUserResponse;
    }


    @Override
    public void deleteBlog(Long id) {

        UserDTO deletedUser = new UserDTO();

        deletedUser = userRepository
                .findById(id)
                .map(UserMapper.INSTANCE::userToUserDto)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", id));

        blogRepository.deleteById(id);

    }

    @Override
    public List<BlogDTO> searchBlogs(String keywords) {
        return null;
    }

}
