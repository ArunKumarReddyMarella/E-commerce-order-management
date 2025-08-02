package com.ecommerce.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelOrderRequest {
    @NotNull(message = "Order ID is required")
    private Long orderId;
    
    @NotBlank(message = "Reason for cancellation is required")
    private String reason;
    
    // Optional: If we want to allow partial cancellation in the future
    private boolean requestRefund = true;
    
    // Optional: User ID for validation (can be used to verify the requestor)
    private Long userId;
}
