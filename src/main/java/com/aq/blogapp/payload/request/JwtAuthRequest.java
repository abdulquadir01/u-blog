package com.aq.blogapp.payload.request;

import lombok.Data;


@Data
public class JwtAuthRequest {
    private String username;
    private String password;
}
