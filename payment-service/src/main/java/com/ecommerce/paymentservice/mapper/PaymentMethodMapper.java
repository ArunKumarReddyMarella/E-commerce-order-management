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
        if (paymentMethod == null) {
            return null;
        }
        
        PaymentMethodDTO dto = new PaymentMethodDTO();
        if (paymentMethod.getId() != null) {
            dto.setId(paymentMethod.getId());
        }
        if (paymentMethod.getUserId() != null) {
            dto.setUserId(paymentMethod.getUserId());
        }
        if (paymentMethod.getType() != null) {
            dto.setType(paymentMethod.getType());
        }
        if (paymentMethod.getProvider() != null) {
            dto.setProvider(paymentMethod.getProvider());
        }
        if (paymentMethod.getAccountNumber() != null) {
            dto.setAccountNumber(paymentMethod.getAccountNumber());
        }
        if (paymentMethod.getExpiry() != null) {
            dto.setExpiry(paymentMethod.getExpiry());
        }
        dto.setDefault(paymentMethod.isDefault());
        return dto;
    }

    public static PaymentMethod toEntity(PaymentMethodDTO dto) {
        if (dto == null) {
            return null;
        }
        
        PaymentMethod paymentMethod = new PaymentMethod();
        if (dto.getId() != null) {
            paymentMethod.setId(dto.getId());
        }
        if (dto.getUserId() != null) {
            paymentMethod.setUserId(dto.getUserId());
        }
        if (dto.getType() != null) {
            paymentMethod.setType(dto.getType());
        }
        if (dto.getProvider() != null) {
            paymentMethod.setProvider(dto.getProvider());
        }
        if (dto.getAccountNumber() != null) {
            paymentMethod.setAccountNumber(dto.getAccountNumber());
        }
        if (dto.getExpiry() != null) {
            paymentMethod.setExpiry(dto.getExpiry());
        }
        paymentMethod.setDefault(dto.isDefault());
        return paymentMethod;
    }
}