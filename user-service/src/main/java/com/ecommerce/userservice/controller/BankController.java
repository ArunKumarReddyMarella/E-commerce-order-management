package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.BankDTO;
import com.ecommerce.userservice.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/banks")
public class BankController {
    @Autowired
    private BankService bankService;

    @PostMapping
    public ResponseEntity<BankDTO> create(@RequestBody BankDTO bankDTO) {
        return ResponseEntity.ok(bankService.createBank(bankDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankDTO> getById(@PathVariable Long id) {
        return bankService.getBankById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankDTO> update(@PathVariable Long id, @RequestBody BankDTO bankDTO) {
        bankDTO.setId(id);
        return ResponseEntity.ok(bankService.updateBank(bankDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bankService.deleteBank(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BankDTO>> list() {
        return ResponseEntity.ok(bankService.listBanks());
    }
} 