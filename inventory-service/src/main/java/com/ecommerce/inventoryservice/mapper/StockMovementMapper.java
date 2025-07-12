package com.ecommerce.inventoryservice.mapper;

import com.ecommerce.inventoryservice.dto.StockMovementDTO;
import com.ecommerce.inventoryservice.model.StockMovement;

public class StockMovementMapper {
    public static StockMovementDTO toDTO(StockMovement stockMovement) {
        StockMovementDTO dto = new StockMovementDTO();
        dto.setId(stockMovement.getId());
        dto.setInventoryId(stockMovement.getInventoryId());
        dto.setQuantityChange(stockMovement.getQuantityChange());
        dto.setMovementType(stockMovement.getMovementType());
        dto.setTimestamp(stockMovement.getTimestamp());
        dto.setReference(stockMovement.getReference());
        return dto;
    }

    public static StockMovement toEntity(StockMovementDTO dto) {
        StockMovement stockMovement = new StockMovement();
        stockMovement.setId(dto.getId());
        stockMovement.setInventoryId(dto.getInventoryId());
        stockMovement.setQuantityChange(dto.getQuantityChange());
        stockMovement.setMovementType(dto.getMovementType());
        stockMovement.setTimestamp(dto.getTimestamp());
        stockMovement.setReference(dto.getReference());
        return stockMovement;
    }
} 