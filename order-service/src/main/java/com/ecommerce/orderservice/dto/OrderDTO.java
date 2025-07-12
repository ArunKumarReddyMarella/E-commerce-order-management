package com.ecommerce.orderservice.dto;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private Long userId;
    private String orderStatus;
    private BigDecimal totalAmount;
    private Instant createdAt;
    private Instant updatedAt;
    private Long addressId;
} 