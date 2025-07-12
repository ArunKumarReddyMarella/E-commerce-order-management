package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.dto.PageRequestDTO;
import com.ecommerce.paymentservice.dto.TransactionDTO;
import com.ecommerce.paymentservice.mapper.TransactionMapper;
import com.ecommerce.paymentservice.model.Transaction;
import com.ecommerce.paymentservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = TransactionMapper.toEntity(transactionDTO);
        Transaction saved = transactionRepository.save(transaction);
        return TransactionMapper.toDTO(saved);
    }

    public Optional<TransactionDTO> getTransactionById(Long id) {
        return transactionRepository.findById(id).map(TransactionMapper::toDTO);
    }

    public TransactionDTO updateTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = TransactionMapper.toEntity(transactionDTO);
        Transaction updated = transactionRepository.save(transaction);
        return TransactionMapper.toDTO(updated);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    public List<TransactionDTO> listTransactions() {
        return transactionRepository.findAll().stream().map(TransactionMapper::toDTO).collect(Collectors.toList());
    }

    public Page<TransactionDTO> listTransactionsPaginated(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortBy());
        Pageable pageable = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        return transactionRepository.findAll(pageable).map(TransactionMapper::toDTO);
    }
} 