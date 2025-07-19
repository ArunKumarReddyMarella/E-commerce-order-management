package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductReviewDTO;
import com.ecommerce.productservice.mapper.ProductReviewMapper;
import com.ecommerce.productservice.model.ProductReview;
import com.ecommerce.productservice.repository.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductReviewService {
    @Autowired
    private ProductReviewRepository productReviewRepository;

    public ProductReviewDTO createProductReview(ProductReviewDTO dto) {
        ProductReview review = ProductReviewMapper.toEntity(dto);
        ProductReview saved = productReviewRepository.save(review);
        return ProductReviewMapper.toDTO(saved);
    }

    public Optional<ProductReviewDTO> getProductReviewById(Long id) {
        return productReviewRepository.findById(id).map(ProductReviewMapper::toDTO);
    }

    public ProductReviewDTO updateProductReview(ProductReviewDTO dto) {
        ProductReview review = ProductReviewMapper.toEntity(dto);
        ProductReview updated = productReviewRepository.save(review);
        return ProductReviewMapper.toDTO(updated);
    }

    public void deleteProductReview(Long id) {
        productReviewRepository.deleteById(id);
    }

    public List<ProductReviewDTO> listProductReviews() {
        return productReviewRepository.findAll().stream().map(ProductReviewMapper::toDTO).collect(Collectors.toList());
    }
} 