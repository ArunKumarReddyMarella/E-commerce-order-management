package com.ecommerce.inventoryservice.repository;

import com.ecommerce.inventoryservice.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
} 