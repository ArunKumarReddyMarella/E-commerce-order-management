package com.ecommerce.inventoryservice.mapper;

import com.ecommerce.inventoryservice.dto.InventoryDTO;
import com.ecommerce.inventoryservice.model.Inventory;

public class InventoryMapper {
    public static InventoryDTO toDTO(Inventory inventory) {
        InventoryDTO dto = new InventoryDTO();
        dto.setId(inventory.getId());
        dto.setProductId(inventory.getProductId());
        dto.setQuantity(inventory.getQuantity());
        dto.setWarehouseId(inventory.getWarehouseId());
        return dto;
    }

    public static Inventory toEntity(InventoryDTO dto) {
        Inventory inventory = new Inventory();
        inventory.setId(dto.getId());
        inventory.setProductId(dto.getProductId());
        inventory.setQuantity(dto.getQuantity());
        inventory.setWarehouseId(dto.getWarehouseId());
        return inventory;
    }
} 