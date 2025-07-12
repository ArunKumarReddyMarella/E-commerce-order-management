package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.dto.PageRequestDTO;
import com.ecommerce.paymentservice.dto.PaymentDTO;
import com.ecommerce.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDTO> create(@RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.ok(paymentService.createPayment(paymentDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getById(@PathVariable Long id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> update(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO) {
        paymentDTO.setId(id);
        return ResponseEntity.ok(paymentService.updatePayment(paymentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> list() {
        return ResponseEntity.ok(paymentService.listPayments());
    }

    @PostMapping("/list")
    public ResponseEntity<Page<PaymentDTO>> listPaginated(@RequestBody PageRequestDTO pageRequestDTO) {
        return ResponseEntity.ok(paymentService.listPaymentsPaginated(pageRequestDTO));
    }
} 