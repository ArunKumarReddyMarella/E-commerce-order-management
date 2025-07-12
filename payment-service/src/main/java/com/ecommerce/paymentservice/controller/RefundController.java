package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.dto.PageRequestDTO;
import com.ecommerce.paymentservice.dto.RefundDTO;
import com.ecommerce.paymentservice.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/refunds")
public class RefundController {
    @Autowired
    private RefundService refundService;

    @PostMapping
    public ResponseEntity<RefundDTO> create(@RequestBody RefundDTO refundDTO) {
        return ResponseEntity.ok(refundService.createRefund(refundDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefundDTO> getById(@PathVariable Long id) {
        return refundService.getRefundById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RefundDTO> update(@PathVariable Long id, @RequestBody RefundDTO refundDTO) {
        refundDTO.setId(id);
        return ResponseEntity.ok(refundService.updateRefund(refundDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        refundService.deleteRefund(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RefundDTO>> list() {
        return ResponseEntity.ok(refundService.listRefunds());
    }

    @PostMapping("/list")
    public ResponseEntity<Page<RefundDTO>> listPaginated(@RequestBody PageRequestDTO pageRequestDTO) {
        return ResponseEntity.ok(refundService.listRefundsPaginated(pageRequestDTO));
    }
} 