package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dto.ProductDTO;
import com.ecommerce.productservice.dto.ProductImageDTO;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.model.ProductImage;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    private ProductMapper() {}
    
    /**
     * Merges non-null fields from ProductDTO into an existing Product entity
     * @param product The existing product to be updated
     * @param dto The DTO containing updated values
     * @return The updated Product entity
     */
    public static Product mergeWithDTO(Product product, ProductDTO dto) {
        if (dto == null || product == null) {
            return product;
        }
        
        if (dto.getTitle() != null) {
            product.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            product.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }
        if (dto.getStock() != null) {
            product.setStock(dto.getStock());
        }
        if (dto.getSku() != null) {
            product.setSku(dto.getSku());
        }
        if (dto.getBrand() != null) {
            product.setBrand(dto.getBrand());
        }
        // Note: Category should be set in the service layer after validation
        
        return product;
    }
    
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
        }
        if (product.getTitle() != null) {
            dto.setTitle(product.getTitle());
        }
        if (product.getDescription() != null) {
            dto.setDescription(product.getDescription());
        }
        if (product.getPrice() != null) {
            dto.setPrice(product.getPrice());
        }
        if (product.getStock() != null) {
            dto.setStock(product.getStock());
        }
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
        }
        if (product.getBrand() != null) {
            dto.setBrand(product.getBrand());
        }

        // Safely handle images
        if (product.getImages() != null) {
            for (ProductImage image : product.getImages()) {
                if (image != null) {
                    ProductImageDTO imageDTO = ProductImageMapper.toDTO(image);
                    if (imageDTO != null) {
                        productImageDTOs.add(imageDTO);
                    }
                }
            }
            dto.setImageDTOlist(productImageDTOs);
        }

        return dto;
    }

    /**
     * Converts a ProductDTO to a Product entity.
     * Note: Category should be set in the service layer.
     *
     * @param dto the ProductDTO to convert, can be null
     * @return the converted Product, or null if the input is null
     */
    public static Product toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        if (dto.getId() != null) {
            product.setId(dto.getId());
        }
        if (dto.getTitle() != null) {
            product.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            product.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }
        if (dto.getStock() != null) {
            product.setStock(dto.getStock());
        }
        if (dto.getBrand() != null) {
            product.setBrand(dto.getBrand());
        }

        // Safely handle images
        if (dto.getImageDTOlist() != null) {
            List<ProductImage> productImages = new ArrayList<>();
            for (ProductImageDTO imageDTO : dto.getImageDTOlist()) {
                if (imageDTO != null) {
                    ProductImage image = ProductImageMapper.toEntity(imageDTO);
                    if (image != null) {
                        productImages.add(image);
                    }
                }
            }
            product.setImages(productImages);
        }

        return product;
    }
}