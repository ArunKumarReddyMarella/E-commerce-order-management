package com.ecommerce.inventoryservice.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovementDTO {
    private Long id;
    private Long inventoryId;
    private Integer quantityChange;
    private String movementType;
    private Instant timestamp;
    private String reference;
    // Getters and setters
} 