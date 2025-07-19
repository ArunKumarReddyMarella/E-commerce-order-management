package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dto.ProductImageDTO;
import com.ecommerce.productservice.model.ProductImage;
import org.springframework.stereotype.Component;

@Component
public class ProductImageMapper {
    private ProductImageMapper() {}
    public static ProductImageDTO toDTO(ProductImage image) {
        return ProductImageDTO.builder()
                .id(image.getId())
                .url(image.getUrl())
                .build();
    }
    public static ProductImage toEntity(ProductImageDTO dto) {
        ProductImage image = new ProductImage();
        image.setId(dto.getId());
        image.setUrl(dto.getUrl());
        // Product must be set in service if needed
        return image;
    }
} 