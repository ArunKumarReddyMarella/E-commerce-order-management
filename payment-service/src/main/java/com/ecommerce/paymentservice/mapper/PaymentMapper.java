package com.ecommerce.paymentservice.mapper;

import com.ecommerce.paymentservice.dto.PaymentDTO;
import com.ecommerce.paymentservice.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    private PaymentMapper() {
        // Private constructor to prevent instantiation
    }

    public static PaymentDTO toDTO(Payment payment) {
        if (payment == null) {
            return null;
        }
        
        PaymentDTO dto = new PaymentDTO();
        if (payment.getId() != null) {
            dto.setId(payment.getId());
        }
        if (payment.getOrderId() != null) {
            dto.setOrderId(payment.getOrderId());
        }
        if (payment.getUserId() != null) {
            dto.setUserId(payment.getUserId());
        }
        if (payment.getAmount() != null) {
            dto.setAmount(payment.getAmount());
        }
        if (payment.getPaymentMethod() != null) {
            dto.setPaymentMethod(payment.getPaymentMethod());
        }
        if (payment.getStatus() != null) {
            dto.setStatus(payment.getStatus());
        }
        if (payment.getTransactionId() != null) {
            dto.setTransactionId(payment.getTransactionId());
        }
        if (payment.getCreatedAt() != null) {
            dto.setCreatedAt(payment.getCreatedAt());
        }
        if (payment.getUpdatedAt() != null) {
            dto.setUpdatedAt(payment.getUpdatedAt());
        }
        return dto;
    }

    public static Payment toEntity(PaymentDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Payment payment = new Payment();
        if (dto.getId() != null) {
            payment.setId(dto.getId());
        }
        if (dto.getOrderId() != null) {
            payment.setOrderId(dto.getOrderId());
        }
        if (dto.getUserId() != null) {
            payment.setUserId(dto.getUserId());
        }
        if (dto.getAmount() != null) {
            payment.setAmount(dto.getAmount());
        }
        if (dto.getPaymentMethod() != null) {
            payment.setPaymentMethod(dto.getPaymentMethod());
        }
        if (dto.getStatus() != null) {
            payment.setStatus(dto.getStatus());
        }
        if (dto.getTransactionId() != null) {
            payment.setTransactionId(dto.getTransactionId());
        }
        if (dto.getCreatedAt() != null) {
            payment.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            payment.setUpdatedAt(dto.getUpdatedAt());
        }
        return payment;
    }
}