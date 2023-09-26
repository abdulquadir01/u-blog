package com.aq.blogapp.utils.mappers;

import com.aq.blogapp.vo.DTO.UserDTO;
import com.aq.blogapp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    //    @Mapping(target = "blogs", source = "")
    User userDtoToUser(UserDTO userDTO);

    UserDTO userToUserDto(User user);
}
