package com.aq.blogapp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
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
    private Date bloggedDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
