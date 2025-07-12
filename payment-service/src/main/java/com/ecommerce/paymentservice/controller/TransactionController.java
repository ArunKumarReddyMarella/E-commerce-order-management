package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.dto.PageRequestDTO;
import com.ecommerce.paymentservice.dto.TransactionDTO;
import com.ecommerce.paymentservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionDTO> create(@RequestBody TransactionDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.createTransaction(transactionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getById(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> update(@PathVariable Long id, @RequestBody TransactionDTO transactionDTO) {
        transactionDTO.setId(id);
        return ResponseEntity.ok(transactionService.updateTransaction(transactionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> list() {
        return ResponseEntity.ok(transactionService.listTransactions());
    }

    @PostMapping("/list")
    public ResponseEntity<Page<TransactionDTO>> listPaginated(@RequestBody PageRequestDTO pageRequestDTO) {
        return ResponseEntity.ok(transactionService.listTransactionsPaginated(pageRequestDTO));
    }
} 