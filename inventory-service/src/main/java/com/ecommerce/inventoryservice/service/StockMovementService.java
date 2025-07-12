package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.dto.StockMovementDTO;
import com.ecommerce.inventoryservice.dto.PageRequestDTO;
import com.ecommerce.inventoryservice.mapper.StockMovementMapper;
import com.ecommerce.inventoryservice.model.StockMovement;
import com.ecommerce.inventoryservice.repository.StockMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockMovementService {
    @Autowired
    private StockMovementRepository stockMovementRepository;

    public StockMovementDTO createStockMovement(StockMovementDTO stockMovementDTO) {
        StockMovement stockMovement = StockMovementMapper.toEntity(stockMovementDTO);
        StockMovement saved = stockMovementRepository.save(stockMovement);
        return StockMovementMapper.toDTO(saved);
    }

    public Optional<StockMovementDTO> getStockMovementById(Long id) {
        return stockMovementRepository.findById(id).map(StockMovementMapper::toDTO);
    }

    public StockMovementDTO updateStockMovement(StockMovementDTO stockMovementDTO) {
        StockMovement stockMovement = StockMovementMapper.toEntity(stockMovementDTO);
        StockMovement updated = stockMovementRepository.save(stockMovement);
        return StockMovementMapper.toDTO(updated);
    }

    public void deleteStockMovement(Long id) {
        stockMovementRepository.deleteById(id);
    }

    public Page<StockMovementDTO> listStockMovements(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortBy());
        PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        return stockMovementRepository.findAll(pageRequest).map(StockMovementMapper::toDTO);
    }
} 