package com.ecommerce.inventoryservice.mapper;

import com.ecommerce.inventoryservice.dto.WarehouseDTO;
import com.ecommerce.inventoryservice.model.Warehouse;

public class WarehouseMapper {
    public static WarehouseDTO toDTO(Warehouse warehouse) {
        WarehouseDTO dto = new WarehouseDTO();
        dto.setId(warehouse.getId());
        dto.setName(warehouse.getName());
        dto.setLocation(warehouse.getLocation());
        dto.setCapacity(warehouse.getCapacity());
        return dto;
    }

    public static Warehouse toEntity(WarehouseDTO dto) {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(dto.getId());
        warehouse.setName(dto.getName());
        warehouse.setLocation(dto.getLocation());
        warehouse.setCapacity(dto.getCapacity());
        return warehouse;
    }
} 