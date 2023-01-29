package com.aq.blogapp.mappers;

import com.aq.blogapp.DTO.BlogDTO;
import com.aq.blogapp.model.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;




@Mapper
public interface BlogMapper {

    BlogMapper INSTANCE = Mappers.getMapper(BlogMapper.class);

    Blog blogDtoToBlog(BlogDTO blogDTO);

    BlogDTO blogToBlogDto(Blog blog);
}
