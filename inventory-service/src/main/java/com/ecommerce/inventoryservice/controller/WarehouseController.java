package com.ecommerce.inventoryservice.controller;

import com.ecommerce.inventoryservice.dto.WarehouseDTO;
import com.ecommerce.inventoryservice.dto.PageRequestDTO;
import com.ecommerce.inventoryservice.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<WarehouseDTO> createWarehouse(@RequestBody WarehouseDTO warehouseDTO) {
        return ResponseEntity.ok(warehouseService.createWarehouse(warehouseDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDTO> getWarehouseById(@PathVariable Long id) {
        Optional<WarehouseDTO> warehouseOpt = warehouseService.getWarehouseById(id);
        return warehouseOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<WarehouseDTO> updateWarehouse(@PathVariable Long id, @RequestBody WarehouseDTO warehouseDTO) {
        warehouseDTO.setId(id);
        return ResponseEntity.ok(warehouseService.updateWarehouse(warehouseDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/list")
    public Page<WarehouseDTO> listWarehouses(@RequestBody PageRequestDTO pageRequestDTO) {
        return warehouseService.listWarehouses(pageRequestDTO);
    }
} 