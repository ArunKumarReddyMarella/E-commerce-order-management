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
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setPaymentId(transaction.getPaymentId());
        dto.setType(transaction.getType());
        dto.setAmount(transaction.getAmount());
        dto.setStatus(transaction.getStatus());
        dto.setCreatedAt(transaction.getCreatedAt());
        return dto;
    }

    public static Transaction toEntity(TransactionDTO dto) {
        Transaction transaction = new Transaction();
        transaction.setId(dto.getId());
        transaction.setPaymentId(dto.getPaymentId());
        transaction.setType(dto.getType());
        transaction.setAmount(dto.getAmount());
        transaction.setStatus(dto.getStatus());
        transaction.setCreatedAt(dto.getCreatedAt());
        return transaction;
    }
} 