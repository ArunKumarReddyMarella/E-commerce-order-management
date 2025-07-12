package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dto.BrandDTO;
import com.ecommerce.productservice.model.Brand;

public class BrandMapper {
    public static BrandDTO toDTO(Brand brand) {
        BrandDTO dto = new BrandDTO();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        dto.setDescription(brand.getDescription());
        return dto;
    }

    public static Brand toEntity(BrandDTO dto) {
        Brand brand = new Brand();
        brand.setId(dto.getId());
        brand.setName(dto.getName());
        brand.setDescription(dto.getDescription());
        return brand;
    }
} 