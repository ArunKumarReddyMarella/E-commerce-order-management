package com.ecommerce.userservice.mapper;

import com.ecommerce.userservice.dto.BankDTO;
import com.ecommerce.userservice.model.Bank;
import org.springframework.stereotype.Component;

@Component
public class BankMapper {
    private BankMapper() {}
    public static BankDTO toDTO(Bank bank) {
        BankDTO dto = new BankDTO();
        dto.setId(bank.getId());
        dto.setUserId(bank.getUserId());
        dto.setCardExpire(bank.getCardExpire());
        dto.setCardNumber(bank.getCardNumber());
        dto.setCardType(bank.getCardType());
        dto.setCurrency(bank.getCurrency());
        dto.setIban(bank.getIban());
        return dto;
    }
    public static Bank toEntity(BankDTO dto) {
        Bank bank = new Bank();
        bank.setId(dto.getId());
        bank.setUserId(dto.getUserId());
        bank.setCardExpire(dto.getCardExpire());
        bank.setCardNumber(dto.getCardNumber());
        bank.setCardType(dto.getCardType());
        bank.setCurrency(dto.getCurrency());
        bank.setIban(dto.getIban());
        return bank;
    }
} 