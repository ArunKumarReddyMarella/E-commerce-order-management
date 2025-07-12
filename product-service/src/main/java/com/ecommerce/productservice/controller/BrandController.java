package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.BrandDTO;
import com.ecommerce.productservice.dto.PageRequestDTO;
import com.ecommerce.productservice.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @PostMapping
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO brandDTO) {
        return ResponseEntity.ok(brandService.createBrand(brandDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable Long id) {
        Optional<BrandDTO> brandOpt = brandService.getBrandById(id);
        return brandOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandDTO> updateBrand(@PathVariable Long id, @RequestBody BrandDTO brandDTO) {
        brandDTO.setId(id);
        return ResponseEntity.ok(brandService.updateBrand(brandDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/list")
    public Page<BrandDTO> listBrands(@RequestBody PageRequestDTO pageRequestDTO) {
        return brandService.listBrands(pageRequestDTO);
    }
} 