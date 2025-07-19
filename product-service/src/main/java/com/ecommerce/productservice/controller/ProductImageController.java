package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ProductImageDTO;
import com.ecommerce.productservice.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/product-images")
public class ProductImageController {
    @Autowired
    private ProductImageService productImageService;

    @PostMapping
    public ResponseEntity<ProductImageDTO> create(@RequestBody ProductImageDTO dto) {
        return ResponseEntity.ok(productImageService.createProductImage(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductImageDTO> getById(@PathVariable Long id) {
        return productImageService.getProductImageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductImageDTO> update(@PathVariable Long id, @RequestBody ProductImageDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(productImageService.updateProductImage(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productImageService.deleteProductImage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductImageDTO>> list() {
        return ResponseEntity.ok(productImageService.listProductImages());
    }
} 