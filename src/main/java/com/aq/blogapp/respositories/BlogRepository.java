package com.aq.blogapp.respositories;

import com.aq.blogapp.model.Blog;
import com.aq.blogapp.model.Category;
import com.aq.blogapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;




public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findAllByUser(User user);

    List<Blog> findAllByCategory(Category category);

}
