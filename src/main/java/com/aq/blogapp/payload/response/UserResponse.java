package com.aq.blogapp.payload.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String about;

    private Set<RoleResponse> roles = new HashSet<>();

}
