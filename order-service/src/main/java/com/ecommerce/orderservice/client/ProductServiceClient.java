package com.ecommerce.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.ecommerce.orderservice.dto.ProductDTO;

@FeignClient(name = "product-service")
public interface ProductServiceClient {
    @GetMapping("/api/products/{productId}")
    ProductDTO getProductById(@PathVariable("productId") Long productId);

    @PostMapping("/api/products/batch")
    List<ProductDTO> getProductsByIds(@RequestBody List<Long> productIds);

    @PutMapping("/api/products/{productId}")
    ProductDTO updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductDTO productDTO);
}