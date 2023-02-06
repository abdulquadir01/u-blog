package com.aq.blogapp.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;



@Getter
@Setter
@NoArgsConstructor
@Entity(name = "roles")
public class Role {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    private String role;

}
