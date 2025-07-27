package com.ecommerce.paymentservice.mapper;

import com.ecommerce.paymentservice.dto.TransactionDTO;
import com.ecommerce.paymentservice.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    private TransactionMapper() {
        // Private constructor to prevent instantiation
    }

    public static TransactionDTO toDTO(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        
        TransactionDTO dto = new TransactionDTO();
        if (transaction.getId() != null) {
            dto.setId(transaction.getId());
        }
        if (transaction.getPaymentId() != null) {
            dto.setPaymentId(transaction.getPaymentId());
        }
        if (transaction.getType() != null) {
            dto.setType(transaction.getType());
        }
        if (transaction.getAmount() != null) {
            dto.setAmount(transaction.getAmount());
        }
        if (transaction.getStatus() != null) {
            dto.setStatus(transaction.getStatus());
        }
        if (transaction.getCreatedAt() != null) {
            dto.setCreatedAt(transaction.getCreatedAt());
        }
        return dto;
    }

    public static Transaction toEntity(TransactionDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Transaction transaction = new Transaction();
        if (dto.getId() != null) {
            transaction.setId(dto.getId());
        }
        if (dto.getPaymentId() != null) {
            transaction.setPaymentId(dto.getPaymentId());
        }
        if (dto.getType() != null) {
            transaction.setType(dto.getType());
        }
        if (dto.getAmount() != null) {
            transaction.setAmount(dto.getAmount());
        }
        if (dto.getStatus() != null) {
            transaction.setStatus(dto.getStatus());
        }
        if (dto.getCreatedAt() != null) {
            transaction.setCreatedAt(dto.getCreatedAt());
        }
        return transaction;
    }
} 