package com.aq.blogapp.model;

import lombok.*;

import javax.persistence.*;




@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String comment;

    private String date;

    @ManyToOne
    @JoinColumn(name = "user_id")
//    @ToString.Exclude
    private User user;


    @ManyToOne
    @JoinColumn(name = "blog_id")
//    @ToString.Exclude
    private Blog blog;

}