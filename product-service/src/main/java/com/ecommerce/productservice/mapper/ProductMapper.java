package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dto.ProductDTO;
import com.ecommerce.productservice.dto.ProductImageDTO;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.model.ProductImage;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    private ProductMapper() {}
    public static ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        dto.setBrand(product.getBrand());
        List<ProductImageDTO> productImageDTOs = new ArrayList<>();
        for(ProductImage image : product.getImages()){
            productImageDTOs.add(ProductImageMapper.toDTO(image));
        }
        dto.setImageDTOlist(productImageDTOs);
        return dto;
    }

    public static Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        // category and brand should be set in the service layer
        product.setBrand(dto.getBrand());
        List<ProductImage> productImages = new ArrayList<>();
        for(ProductImageDTO imageDTO: dto.getImageDTOlist()){
            productImages.add(ProductImageMapper.toEntity(imageDTO));
        }
        product.setImages(productImages);
        return product;
    }
} 