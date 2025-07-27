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
        if (refund == null) {
            return null;
        }
        
        RefundDTO dto = new RefundDTO();
        if (refund.getId() != null) {
            dto.setId(refund.getId());
        }
        if (refund.getPaymentId() != null) {
            dto.setPaymentId(refund.getPaymentId());
        }
        if (refund.getAmount() != null) {
            dto.setAmount(refund.getAmount());
        }
        if (refund.getReason() != null) {
            dto.setReason(refund.getReason());
        }
        if (refund.getStatus() != null) {
            dto.setStatus(refund.getStatus());
        }
        if (refund.getCreatedAt() != null) {
            dto.setCreatedAt(refund.getCreatedAt());
        }
        return dto;
    }

    public static Refund toEntity(RefundDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Refund refund = new Refund();
        if (dto.getId() != null) {
            refund.setId(dto.getId());
        }
        if (dto.getPaymentId() != null) {
            refund.setPaymentId(dto.getPaymentId());
        }
        if (dto.getAmount() != null) {
            refund.setAmount(dto.getAmount());
        }
        if (dto.getReason() != null) {
            refund.setReason(dto.getReason());
        }
        if (dto.getStatus() != null) {
            refund.setStatus(dto.getStatus());
        }
        if (dto.getCreatedAt() != null) {
            refund.setCreatedAt(dto.getCreatedAt());
        }
        return refund;
    }
}