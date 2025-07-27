package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dto.CategoryDTO;
import com.ecommerce.productservice.model.Category;

public class CategoryMapper {
    public static CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        if (category.getId() != null) {
            dto.setId(category.getId());
        }
        if (category.getName() != null) {
            dto.setName(category.getName());
        }
        if (category.getDescription() != null) {
            dto.setDescription(category.getDescription());
        }
        return dto;
    }

    public static Category toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Category category = new Category();
        if (dto.getId() != null) {
            category.setId(dto.getId());
        }
        if (dto.getName() != null) {
            category.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            category.setDescription(dto.getDescription());
        }
        return category;
    }
} 