package com.aq.blogapp.utils.mappers;

import com.aq.blogapp.vo.DTO.BlogDTO;
import com.aq.blogapp.entity.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface BlogMapper {

    BlogMapper INSTANCE = Mappers.getMapper(BlogMapper.class);

    Blog blogDtoToBlog(BlogDTO blogDTO);

    BlogDTO blogToBlogDto(Blog blog);
}
