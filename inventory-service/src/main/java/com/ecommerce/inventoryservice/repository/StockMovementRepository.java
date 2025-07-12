package com.ecommerce.inventoryservice.repository;

import com.ecommerce.inventoryservice.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
} 