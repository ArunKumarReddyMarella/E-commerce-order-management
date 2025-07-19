package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.BankDTO;
import com.ecommerce.userservice.mapper.BankMapper;
import com.ecommerce.userservice.model.Bank;
import com.ecommerce.userservice.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankService {
    @Autowired
    private BankRepository bankRepository;

    public BankDTO createBank(BankDTO bankDTO) {
        Bank bank = BankMapper.toEntity(bankDTO);
        Bank saved = bankRepository.save(bank);
        return BankMapper.toDTO(saved);
    }

    public Optional<BankDTO> getBankById(Long id) {
        return bankRepository.findById(id).map(BankMapper::toDTO);
    }

    public BankDTO updateBank(BankDTO bankDTO) {
        Bank bank = BankMapper.toEntity(bankDTO);
        Bank updated = bankRepository.save(bank);
        return BankMapper.toDTO(updated);
    }

    public void deleteBank(Long id) {
        bankRepository.deleteById(id);
    }

    public List<BankDTO> listBanks() {
        return bankRepository.findAll().stream().map(BankMapper::toDTO).collect(Collectors.toList());
    }
} 