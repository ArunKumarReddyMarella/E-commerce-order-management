package com.ecommerce.paymentservice.mapper;

import com.ecommerce.paymentservice.dto.PaymentMethodDTO;
import com.ecommerce.paymentservice.model.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodMapper {

    private PaymentMethodMapper() {
        // Private constructor to prevent instantiation
    }

    public static PaymentMethodDTO toDTO(PaymentMethod paymentMethod) {
        PaymentMethodDTO dto = new PaymentMethodDTO();
        dto.setId(paymentMethod.getId());
        dto.setUserId(paymentMethod.getUserId());
        dto.setType(paymentMethod.getType());
        dto.setProvider(paymentMethod.getProvider());
        dto.setAccountNumber(paymentMethod.getAccountNumber());
        dto.setExpiry(paymentMethod.getExpiry());
        dto.setDefault(paymentMethod.isDefault());
        return dto;
    }

    public static PaymentMethod toEntity(PaymentMethodDTO dto) {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(dto.getId());
        paymentMethod.setUserId(dto.getUserId());
        paymentMethod.setType(dto.getType());
        paymentMethod.setProvider(dto.getProvider());
        paymentMethod.setAccountNumber(dto.getAccountNumber());
        paymentMethod.setExpiry(dto.getExpiry());
        paymentMethod.setDefault(dto.isDefault());
        return paymentMethod;
    }
} 