package com.ecommerce.orderservice.mapper;

import com.ecommerce.orderservice.dto.OrderItemDTO;
import com.ecommerce.orderservice.dto.OrderItemRequest;
import com.ecommerce.orderservice.model.OrderItem;

public class OrderItemMapper {
    public static OrderItemDTO toDTO(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        
        OrderItemDTO dto = new OrderItemDTO();
        if (orderItem.getId() != null) {
            dto.setId(orderItem.getId());
        }
        if (orderItem.getOrderId() != null) {
            dto.setOrderId(orderItem.getOrderId());
        }
        if (orderItem.getProductId() != null) {
            dto.setProductId(orderItem.getProductId());
        }
        if (orderItem.getQuantity() != null) {
            dto.setQuantity(orderItem.getQuantity());
        }
        if (orderItem.getPrice() != null) {
            dto.setPrice(orderItem.getPrice());
        }
        return dto;
    }

    public static OrderItem toEntity(OrderItemRequest request, Long orderId) {
        if (request == null) {
            return null;
        }
        
        OrderItem orderItem = new OrderItem();
        if (orderId != null) {
            orderItem.setOrderId(orderId);
        }
        if (request.getProductId() != null) {
            orderItem.setProductId(request.getProductId());
        }
        if (request.getQuantity() != null) {
            orderItem.setQuantity(request.getQuantity());
        }
        // Price is intentionally left null to be set by the service
        return orderItem;
    }
    
    /**
     * Converts an OrderItemDTO to an OrderItem entity.
     *
     * @param dto the OrderItemDTO to convert, can be null
     * @return the converted OrderItem, or null if the input is null
     */
    public static OrderItem toEntity(OrderItemDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return OrderItem.builder()
                .id(dto.getId())
                .orderId(dto.getOrderId())
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .price(dto.getPrice())
                .build();
    }
}