package com.aq.blogapp.payload.response;

import lombok.Data;


@Data
public class JwtAuthResponse {

    private String token;
    private Long userId;

}
