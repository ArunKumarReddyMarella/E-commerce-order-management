package com.ecommerce.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankDTO {
    private Long id;
    private Long userId;
    private String cardExpire;
    private String cardNumber;
    private String cardType;
    private String currency;
    private String iban;
} 