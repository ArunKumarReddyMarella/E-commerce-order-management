package com.ecommerce.paymentservice.mapper;

import com.ecommerce.paymentservice.dto.RefundDTO;
import com.ecommerce.paymentservice.model.Refund;
import org.springframework.stereotype.Component;

@Component
public class RefundMapper {

    private RefundMapper() {
        // Private constructor to prevent instantiation
    }

    public static RefundDTO toDTO(Refund refund) {
        RefundDTO dto = new RefundDTO();
        dto.setId(refund.getId());
        dto.setPaymentId(refund.getPaymentId());
        dto.setAmount(refund.getAmount());
        dto.setReason(refund.getReason());
        dto.setStatus(refund.getStatus());
        dto.setCreatedAt(refund.getCreatedAt());
        return dto;
    }

    public static Refund toEntity(RefundDTO dto) {
        Refund refund = new Refund();
        refund.setId(dto.getId());
        refund.setPaymentId(dto.getPaymentId());
        refund.setAmount(dto.getAmount());
        refund.setReason(dto.getReason());
        refund.setStatus(dto.getStatus());
        refund.setCreatedAt(dto.getCreatedAt());
        return refund;
    }
} 