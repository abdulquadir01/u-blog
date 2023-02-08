package com.aq.blogapp.model;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;



@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogId;

    @NotEmpty
    @Column(name = "blog_title", nullable = false, length = 256)
    private String title;

    @NotEmpty
    @Column(name = "blog_content", nullable = false, length = 4096)
    private String content;

    //    @Column(name = "blog_image", nullable = false, length = 128)
    private String imageName;

    //    @Column(name = "blogging_date",nullable = false, length = 128)
    private String bloggedDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Blog blog = (Blog) o;
        return blogId != null && Objects.equals(blogId, blog.blogId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
