package com.aq.blogapp.services;

import com.aq.blogapp.DTO.BlogDTO;
import com.aq.blogapp.DTO.UserDTO;
import com.aq.blogapp.exceptions.ResourceNotFoundException;
import com.aq.blogapp.mappers.BlogMapper;
import com.aq.blogapp.mappers.UserMapper;
import com.aq.blogapp.model.Blog;
import com.aq.blogapp.model.Category;
import com.aq.blogapp.model.User;
import com.aq.blogapp.respositories.BlogRepository;
import com.aq.blogapp.respositories.CategoryRepository;
import com.aq.blogapp.respositories.UserRepository;
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

    public BlogServiceImpl(BlogRepository blogRepository, BlogMapper blogMapper, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.blogRepository = blogRepository;
        this.blogMapper = blogMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Blog> getAllBlog() {
        List<Blog> blogs = new ArrayList<>();

        blogs = blogRepository.findAll();
        return blogs;
    }

    @Override
    public BlogDTO getBlogById(Long id) {
        BlogDTO blogDTO = new BlogDTO();

        try{
            if(id !=null) {
                blogDTO = blogRepository
                        .findById(id)
                        .map(blogMapper::blogToBlogDto)
                        .orElseThrow(() -> new ResourceNotFoundException("Blog", "BlogId", id));
            }
        }
        catch (NoSuchElementException ex){
            throw new ResourceNotFoundException("Blog", "BlogId", id);
        }

        return blogDTO;
    }

    @Override
    public BlogDTO createBlog(BlogDTO blogDTO, Long userId, Long categoryId) {
        BlogDTO newBlogDTO = new BlogDTO();

        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Blog newBlog = blogMapper.blogDtoToBlog(blogDTO);
        newBlog.setImageName("default.png");
        newBlog.setBloggedDate(new Date());
        newBlog.setUser(user);
        newBlog.setCategory(category);

        Blog createdBlog = blogRepository.save(newBlog);

        newBlogDTO = blogMapper.blogToBlogDto(createdBlog);

        return newBlogDTO;
    }

    @Override
    public BlogDTO updateBlog(Long id, BlogDTO blogDTO) {
        BlogDTO updatedBlog = new BlogDTO();

        try{

            Blog exitingBlog = blogRepository
                    .findById(id)
                    .orElseThrow( ()-> new ResourceNotFoundException("Blog", "blogId", id));

            exitingBlog.setTitle(blogDTO.getTitle());
            exitingBlog.setContent(blogDTO.getContent());
            exitingBlog.setImageName(blogDTO.getImageName());

            Blog savedBlog = blogRepository.save(exitingBlog);

            updatedBlog = blogMapper.blogToBlogDto(savedBlog);


        }catch (Exception ex){
            throw new ResourceNotFoundException("Blog", "blogId", id);
        }

        return updatedBlog;
    }

    @Override
    public List<BlogDTO> getBlogsByCategory(Long categoryId) {

        Category category = categoryRepository
                                .findById(categoryId)
                                .orElseThrow( ()-> new ResourceNotFoundException("Category", "categoryId", categoryId) );

        List<BlogDTO> allBlogsByCategory = blogRepository
                                        .findAllByCategory(category)
                                        .stream()
                                        .map( blog -> blogMapper.blogToBlogDto(blog))
                                        .collect(Collectors.toList());

        return allBlogsByCategory;
    }



    @Override
    public List<BlogDTO> getBlogsByUser(Long userId) {

        User user = userRepository
                        .findById(userId)
                        .orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));

        List<BlogDTO> allBlogsByUser = blogRepository
                                        .findAllByUser(user)
                                        .stream()
                                        .map( blog -> blogMapper.blogToBlogDto(blog))
                                        .collect(Collectors.toList());

        return allBlogsByUser;
    }

    @Override
    public void deleteBlog(Long id) {

        UserDTO deletedUser = userRepository
                .findById(id)
                .map(UserMapper.INSTANCE::userToUserDto)
                .orElseThrow( ()-> new ResourceNotFoundException("User", "userId", id) );

        blogRepository.deleteById(id);

    }

    @Override
    public List<BlogDTO> searchBlogs(String keywords) {
        return null;
    }
}
