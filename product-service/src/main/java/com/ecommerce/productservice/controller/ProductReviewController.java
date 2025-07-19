package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ProductReviewDTO;
import com.ecommerce.productservice.service.ProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/product-reviews")
public class ProductReviewController {
    @Autowired
    private ProductReviewService productReviewService;

    @PostMapping
    public ResponseEntity<ProductReviewDTO> create(@RequestBody ProductReviewDTO dto) {
        return ResponseEntity.ok(productReviewService.createProductReview(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductReviewDTO> getById(@PathVariable Long id) {
        return productReviewService.getProductReviewById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductReviewDTO> update(@PathVariable Long id, @RequestBody ProductReviewDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(productReviewService.updateProductReview(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productReviewService.deleteProductReview(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductReviewDTO>> list() {
        return ResponseEntity.ok(productReviewService.listProductReviews());
    }
} 