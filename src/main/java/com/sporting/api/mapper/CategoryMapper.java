package com.sporting.api.mapper;

import com.sporting.api.dto.CategoryDTO;
import com.sporting.api.entity.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper extends AbstractMapper<Category, CategoryDTO> {
    public CategoryMapper() {
        super(Category.class, CategoryDTO.class);
    }
}
