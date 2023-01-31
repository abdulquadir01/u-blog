package com.aq.blogapp.mappers;

import com.aq.blogapp.DTO.CommentDTO;
import com.aq.blogapp.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDTO commentToCommentDto(Comment comment);

    Comment commentDtoToComment(CommentDTO commentDTO);

}
