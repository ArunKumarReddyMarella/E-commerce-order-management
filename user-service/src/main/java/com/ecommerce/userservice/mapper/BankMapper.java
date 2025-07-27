package com.ecommerce.userservice.mapper;

import com.ecommerce.userservice.dto.BankDTO;
import com.ecommerce.userservice.model.Bank;
import org.springframework.stereotype.Component;

@Component
public class BankMapper {
    private BankMapper() {}
    
    public static BankDTO toDTO(Bank bank) {
        if (bank == null) {
            return null;
        }
        
        BankDTO dto = new BankDTO();
        if (bank.getId() != null) {
            dto.setId(bank.getId());
        }
        if (bank.getUserId() != null) {
            dto.setUserId(bank.getUserId());
        }
        if (bank.getCardExpire() != null) {
            dto.setCardExpire(bank.getCardExpire());
        }
        if (bank.getCardNumber() != null) {
            dto.setCardNumber(bank.getCardNumber());
        }
        if (bank.getCardType() != null) {
            dto.setCardType(bank.getCardType());
        }
        if (bank.getCurrency() != null) {
            dto.setCurrency(bank.getCurrency());
        }
        if (bank.getIban() != null) {
            dto.setIban(bank.getIban());
        }
        return dto;
    }
    
    public static Bank toEntity(BankDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Bank bank = new Bank();
        if (dto.getId() != null) {
            bank.setId(dto.getId());
        }
        if (dto.getUserId() != null) {
            bank.setUserId(dto.getUserId());
        }
        if (dto.getCardExpire() != null) {
            bank.setCardExpire(dto.getCardExpire());
        }
        if (dto.getCardNumber() != null) {
            bank.setCardNumber(dto.getCardNumber());
        }
        if (dto.getCardType() != null) {
            bank.setCardType(dto.getCardType());
        }
        if (dto.getCurrency() != null) {
            bank.setCurrency(dto.getCurrency());
        }
        if (dto.getIban() != null) {
            bank.setIban(dto.getIban());
        }
        return bank;
    }
} 