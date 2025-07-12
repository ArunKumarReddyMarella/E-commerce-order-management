package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.dto.InventoryDTO;
import com.ecommerce.inventoryservice.dto.PageRequestDTO;
import com.ecommerce.inventoryservice.mapper.InventoryMapper;
import com.ecommerce.inventoryservice.model.Inventory;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public InventoryDTO createInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = InventoryMapper.toEntity(inventoryDTO);
        Inventory saved = inventoryRepository.save(inventory);
        return InventoryMapper.toDTO(saved);
    }

    public Optional<InventoryDTO> getInventoryById(Long id) {
        return inventoryRepository.findById(id).map(InventoryMapper::toDTO);
    }

    public InventoryDTO updateInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = InventoryMapper.toEntity(inventoryDTO);
        Inventory updated = inventoryRepository.save(inventory);
        return InventoryMapper.toDTO(updated);
    }

    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }

    public Page<InventoryDTO> listInventory(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortBy());
        PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        return inventoryRepository.findAll(pageRequest).map(InventoryMapper::toDTO);
    }

    public Inventory adjustStock(Long id, int quantityChange) {
        Optional<Inventory> invOpt = inventoryRepository.findById(id);
        if (invOpt.isPresent()) {
            Inventory inv = invOpt.get();
            inv.setQuantity(inv.getQuantity() + quantityChange);
            return inventoryRepository.save(inv);
        }
        return null;
    }
} 