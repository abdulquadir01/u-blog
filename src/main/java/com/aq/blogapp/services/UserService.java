package com.aq.blogapp.services;

import com.aq.blogapp.payload.DTO.UserDTO;
import com.aq.blogapp.payload.response.UsersResponse;

import java.util.List;


public interface UserService {


    UserDTO registerUser(UserDTO user);

   UsersResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    UserDTO getUserById(Long id);

    UserDTO getUserByEmail(String email);

    UserDTO createUser(UserDTO user);

    UserDTO updateUser(Long id, UserDTO user);

    void deleteUser(Long id);

}
