package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dto.ProductImageDTO;
import com.ecommerce.productservice.model.ProductImage;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between ProductImage and ProductImageDTO objects.
 * Includes null checks to prevent NullPointerExceptions.
 */
@Component
public class ProductImageMapper {

    private ProductImageMapper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts a ProductImage entity to a ProductImageDTO.
     *
     * @param image the ProductImage to convert, can be null
     * @return the converted ProductImageDTO, or null if the input is null
     */
    public static ProductImageDTO toDTO(ProductImage image) {
        if (image == null) {
            return null;
        }
        
        return ProductImageDTO.builder()
                .id(image.getId())
                .url(image.getUrl())
                .build();
    }

    /**
     * Converts a ProductImageDTO to a ProductImage entity.
     *
     * @param dto the ProductImageDTO to convert, can be null
     * @return the converted ProductImage, or null if the input is null
     */
    public static ProductImage toEntity(ProductImageDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ProductImage image = new ProductImage();
        if (dto.getId() != null) {
            image.setId(dto.getId());
        }
        if (dto.getUrl() != null) {
            image.setUrl(dto.getUrl());
        }
        // Product must be set in service if needed
        return image;
    }
}