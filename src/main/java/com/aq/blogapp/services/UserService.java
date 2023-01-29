package com.aq.blogapp.services;

import com.aq.blogapp.DTO.UserDTO;

import java.util.List;




public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);

}
