package com.aq.blogapp.mappers;

import com.aq.blogapp.DTO.CategoryDTO;
import com.aq.blogapp.DTO.UserDTO;
import com.aq.blogapp.model.Category;
import com.aq.blogapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category categoryDtoToCategory(CategoryDTO categoryDTO);

    CategoryDTO categoryToCategoryDto(Category category);
}
