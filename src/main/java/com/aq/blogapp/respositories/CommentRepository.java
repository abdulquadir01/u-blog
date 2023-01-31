package com.aq.blogapp.respositories;

import com.aq.blogapp.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {

//    Page<Comment> findAllByCategory(Category category, Pageable pageable);

    Page<Comment> findAllByUser(Comment comment, Pageable pageable);

//    List<Comment> findByTitleContaining(String title);
}
