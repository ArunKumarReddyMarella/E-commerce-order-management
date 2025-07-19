package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dto.ProductReviewDTO;
import com.ecommerce.productservice.model.ProductReview;
import org.springframework.stereotype.Component;

@Component
public class ProductReviewMapper {
    private ProductReviewMapper() {}
    public static ProductReviewDTO toDTO(ProductReview review) {
        return ProductReviewDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .date(review.getDate())
                .reviewerName(review.getReviewerName())
                .reviewerEmail(review.getReviewerEmail())
                .build();
    }
    public static ProductReview toEntity(ProductReviewDTO dto) {
        ProductReview review = new ProductReview();
        review.setId(dto.getId());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setDate(dto.getDate());
        review.setReviewerName(dto.getReviewerName());
        review.setReviewerEmail(dto.getReviewerEmail());
        // Product must be set in service if needed
        return review;
    }
} 