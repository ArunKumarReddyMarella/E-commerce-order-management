package com.ecommerce.inventoryservice.controller;

import com.ecommerce.inventoryservice.dto.StockMovementDTO;
import com.ecommerce.inventoryservice.dto.PageRequestDTO;
import com.ecommerce.inventoryservice.service.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/stock-movements")
public class StockMovementController {
    @Autowired
    private StockMovementService stockMovementService;

    @PostMapping
    public ResponseEntity<StockMovementDTO> createStockMovement(@RequestBody StockMovementDTO stockMovementDTO) {
        return ResponseEntity.ok(stockMovementService.createStockMovement(stockMovementDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockMovementDTO> getStockMovementById(@PathVariable Long id) {
        Optional<StockMovementDTO> stockMovementOpt = stockMovementService.getStockMovementById(id);
        return stockMovementOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockMovementDTO> updateStockMovement(@PathVariable Long id, @RequestBody StockMovementDTO stockMovementDTO) {
        stockMovementDTO.setId(id);
        return ResponseEntity.ok(stockMovementService.updateStockMovement(stockMovementDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockMovement(@PathVariable Long id) {
        stockMovementService.deleteStockMovement(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/list")
    public Page<StockMovementDTO> listStockMovements(@RequestBody PageRequestDTO pageRequestDTO) {
        return stockMovementService.listStockMovements(pageRequestDTO);
    }
} 