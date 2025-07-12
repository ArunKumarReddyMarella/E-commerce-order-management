package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.dto.WarehouseDTO;
import com.ecommerce.inventoryservice.dto.PageRequestDTO;
import com.ecommerce.inventoryservice.mapper.WarehouseMapper;
import com.ecommerce.inventoryservice.model.Warehouse;
import com.ecommerce.inventoryservice.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WarehouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;

    public WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = WarehouseMapper.toEntity(warehouseDTO);
        Warehouse saved = warehouseRepository.save(warehouse);
        return WarehouseMapper.toDTO(saved);
    }

    public Optional<WarehouseDTO> getWarehouseById(Long id) {
        return warehouseRepository.findById(id).map(WarehouseMapper::toDTO);
    }

    public WarehouseDTO updateWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = WarehouseMapper.toEntity(warehouseDTO);
        Warehouse updated = warehouseRepository.save(warehouse);
        return WarehouseMapper.toDTO(updated);
    }

    public void deleteWarehouse(Long id) {
        warehouseRepository.deleteById(id);
    }

    public Page<WarehouseDTO> listWarehouses(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortBy());
        PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        return warehouseRepository.findAll(pageRequest).map(WarehouseMapper::toDTO);
    }
} 