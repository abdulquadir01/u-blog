package com.aq.blogapp.utils.mappers;

import com.aq.blogapp.vo.DTO.CommentDTO;
import com.aq.blogapp.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDTO commentToCommentDto(Comment comment);

    Comment commentDtoToComment(CommentDTO commentDTO);

}
