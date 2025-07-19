package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductImageDTO;
import com.ecommerce.productservice.mapper.ProductImageMapper;
import com.ecommerce.productservice.model.ProductImage;
import com.ecommerce.productservice.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;

    public ProductImageDTO createProductImage(ProductImageDTO dto) {
        ProductImage image = ProductImageMapper.toEntity(dto);
        ProductImage saved = productImageRepository.save(image);
        return ProductImageMapper.toDTO(saved);
    }

    public Optional<ProductImageDTO> getProductImageById(Long id) {
        return productImageRepository.findById(id).map(ProductImageMapper::toDTO);
    }

    public ProductImageDTO updateProductImage(ProductImageDTO dto) {
        ProductImage image = ProductImageMapper.toEntity(dto);
        ProductImage updated = productImageRepository.save(image);
        return ProductImageMapper.toDTO(updated);
    }

    public void deleteProductImage(Long id) {
        productImageRepository.deleteById(id);
    }

    public List<ProductImageDTO> listProductImages() {
        return productImageRepository.findAll().stream().map(ProductImageMapper::toDTO).collect(Collectors.toList());
    }
} 