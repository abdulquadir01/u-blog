package com.aq.blogapp.respositories;

import com.aq.blogapp.model.Category;
import com.aq.blogapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {

}
