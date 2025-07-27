package com.ecommerce.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Order status is required")
    private String orderStatus;
    
    @Valid
    @NotEmpty(message = "At least one order item is required")
    private List<OrderItemRequest> orderItems;
    
    @Valid
    @NotNull(message = "Shipping address is required")
    private AddressDTO shippingAddress;
    
    // Add any additional fields that might be needed for order creation
    // private String paymentMethod;
}
