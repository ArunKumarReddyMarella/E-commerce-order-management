package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.dto.PageRequestDTO;
import com.ecommerce.paymentservice.dto.PaymentMethodDTO;
import com.ecommerce.paymentservice.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {
    @Autowired
    private PaymentMethodService paymentMethodService;

    @PostMapping
    public ResponseEntity<PaymentMethodDTO> create(@RequestBody PaymentMethodDTO paymentMethodDTO) {
        return ResponseEntity.ok(paymentMethodService.createPaymentMethod(paymentMethodDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodDTO> getById(@PathVariable Long id) {
        return paymentMethodService.getPaymentMethodById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethodDTO> update(@PathVariable Long id, @RequestBody PaymentMethodDTO paymentMethodDTO) {
        paymentMethodDTO.setId(id);
        return ResponseEntity.ok(paymentMethodService.updatePaymentMethod(paymentMethodDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentMethodService.deletePaymentMethod(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PaymentMethodDTO>> list() {
        return ResponseEntity.ok(paymentMethodService.listPaymentMethods());
    }

    @PostMapping("/list")
    public ResponseEntity<Page<PaymentMethodDTO>> listPaginated(@RequestBody PageRequestDTO pageRequestDTO) {
        return ResponseEntity.ok(paymentMethodService.listPaymentMethodsPaginated(pageRequestDTO));
    }
} 