package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.dto.ProductReviewDTO;
import com.ecommerce.productservice.model.ProductReview;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between ProductReview and ProductReviewDTO objects.
 * Includes null checks to prevent NullPointerExceptions.
 */
@Component
public class ProductReviewMapper {

    private ProductReviewMapper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts a ProductReview entity to a ProductReviewDTO.
     *
     * @param review the ProductReview to convert, can be null
     * @return the converted ProductReviewDTO, or null if the input is null
     */
    public static ProductReviewDTO toDTO(ProductReview review) {
        if (review == null) {
            return null;
        }
        
        return ProductReviewDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .date(review.getDate())
                .reviewerName(review.getReviewerName())
                .reviewerEmail(review.getReviewerEmail())
                .build();
    }

    /**
     * Converts a ProductReviewDTO to a ProductReview entity.
     * Note: Product should be set in the service layer.
     *
     * @param dto the ProductReviewDTO to convert, can be null
     * @return the converted ProductReview, or null if the input is null
     */
    public static ProductReview toEntity(ProductReviewDTO dto) {
        if (dto == null) {
            return null;
        }
        
        ProductReview review = new ProductReview();
        if (dto.getId() != null) {
            review.setId(dto.getId());
        }
        if (dto.getRating() != null) {
            review.setRating(dto.getRating());
        }
        if (dto.getComment() != null) {
            review.setComment(dto.getComment());
        }
        if (dto.getDate() != null) {
            review.setDate(dto.getDate());
        }
        if (dto.getReviewerName() != null) {
            review.setReviewerName(dto.getReviewerName());
        }
        if (dto.getReviewerEmail() != null) {
            review.setReviewerEmail(dto.getReviewerEmail());
        }
        // Product must be set in service if needed
        return review;
    }
}