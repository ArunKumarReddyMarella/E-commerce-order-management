package com.ecommerce.paymentservice.dto;

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
public class TransactionDTO {
    private Long id;
    private Long paymentId;
    private String type;
    private BigDecimal amount;
    private String status;
    private Instant createdAt;
    // Getters and setters
} 