package com.aq.blogapp.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;


    @ManyToOne
    @JoinColumn(name = "blog_id")
    @ToString.Exclude
    private Blog blog;

}