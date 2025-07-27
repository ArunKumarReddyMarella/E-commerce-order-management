package com.ecommerce.orderservice.mapper;

import com.ecommerce.orderservice.dto.OrderDTO;
import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.model.Order;

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
        if (request.getOrderStatus() != null) {
            order.setOrderStatus(request.getOrderStatus());
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
}