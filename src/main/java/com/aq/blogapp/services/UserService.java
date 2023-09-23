package com.aq.blogapp.services;

import com.aq.blogapp.vo.DTO.UserDTO;

import java.util.List;


public interface UserService {


    UserDTO registerUser(UserDTO user);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO getUserByEmail(String email);

    UserDTO createUser(UserDTO user);

    UserDTO updateUser(Long id, UserDTO user);

    void deleteUser(Long id);

}
