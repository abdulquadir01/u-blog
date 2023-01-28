package com.aq.blogapp.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "title", nullable = false, length = 64)
    private String categoryTitle;

    @Column(name = "description", nullable = false, length = 256)
    private String categoryDescription;
}
