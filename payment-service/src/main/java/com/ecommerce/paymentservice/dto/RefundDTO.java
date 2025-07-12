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
public class RefundDTO {
    private Long id;
    private Long paymentId;
    private BigDecimal amount;
    private String reason;
    private String status;
    private Instant createdAt;
    // Getters and setters
} 