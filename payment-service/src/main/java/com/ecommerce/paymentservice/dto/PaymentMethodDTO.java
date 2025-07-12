package com.ecommerce.paymentservice.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethodDTO {
    private Long id;
    private Long userId;
    private String type;
    private String provider;
    private String accountNumber;
    private LocalDate expiry;
    private boolean isDefault;
    // Getters and setters
} 