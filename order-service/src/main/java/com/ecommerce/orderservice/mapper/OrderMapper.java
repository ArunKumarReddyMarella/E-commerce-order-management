package com.ecommerce.orderservice.mapper;

import com.ecommerce.orderservice.dto.OrderDTO;
import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderStatus;

import java.time.Instant;

public class OrderMapper {
    public static OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }
        
        OrderDTO dto = new OrderDTO();
        if (order.getId() != null) {
            dto.setId(order.getId());
        }
        if (order.getUserId() != null) {
            dto.setUserId(order.getUserId());
        }
        if (order.getOrderStatus() != null) {
            dto.setOrderStatus(order.getOrderStatus());
        }
        if (order.getTotalAmount() != null) {
            dto.setTotalAmount(order.getTotalAmount());
        }
        if (order.getCreatedAt() != null) {
            dto.setCreatedAt(order.getCreatedAt());
        }
        if (order.getUpdatedAt() != null) {
            dto.setUpdatedAt(order.getUpdatedAt());
        }
        if (order.getAddressId() != null) {
            dto.setAddressId(order.getAddressId());
        }
        return dto;
    }

    public static Order toEntity(OrderRequest request) {
        if (request == null) {
            return null;
        }
        
        Order order = new Order();
        if (request.getUserId() != null) {
            order.setUserId(request.getUserId());
        }
        // Default to CREATED status if not specified
        order.setOrderStatus(OrderStatus.CREATED);
        if (request.getOrderStatus() != null) {
            try {
                order.setOrderStatus(OrderStatus.valueOf(request.getOrderStatus()));
            } catch (IllegalArgumentException e) {
                // If invalid status provided, default to CREATED
                order.setOrderStatus(OrderStatus.CREATED);
            }
        }
        if (request.getShippingAddress() != null && request.getShippingAddress().getId() != null) {
            order.setAddressId(request.getShippingAddress().getId());
        }
        return order;
    }

    /**
     * Converts an OrderDTO to an Order entity.
     *
     * @param dto the OrderDTO to convert, can be null
     * @return the converted Order, or null if the input is null
     */
    public static Order toEntity(OrderDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Order.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .orderStatus(dto.getOrderStatus())
                .totalAmount(dto.getTotalAmount())
                .addressId(dto.getAddressId())
                .build();
    }

    /**
     * Merges non-null fields from OrderDTO into an existing Order entity
     * @param order The existing order to be updated
     * @param dto The DTO containing updated values
     * @return The updated Order entity
     */
    public static Order mergeWithDTO(Order order, OrderDTO dto) {
        if (dto == null || order == null) {
            return order;
        }
        
        if (dto.getOrderStatus() != null) {
            order.setOrderStatus(dto.getOrderStatus());
        }
        if (dto.getTotalAmount() != null) {
            order.setTotalAmount(dto.getTotalAmount());
        }
        if (dto.getAddressId() != null) {
            order.setAddressId(dto.getAddressId());
        }
        // Note: userId is typically not updated after order creation
        
        return order;
    }
}