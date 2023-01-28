package com.aq.blogapp.mappers;

import com.aq.blogapp.DTO.UserDTO;
import com.aq.blogapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDtoToUser(UserDTO userDTO);

    UserDTO userToUserDto(User user);
}
