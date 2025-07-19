package com.ecommerce.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReviewDTO {
    private Long id;
    private Integer rating;
    private String comment;
    private Instant date;
    private String reviewerName;
    private String reviewerEmail;
    private Long productId;
} 