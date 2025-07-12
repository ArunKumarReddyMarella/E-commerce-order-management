package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderItemDTO;
import com.ecommerce.orderservice.dto.PageRequestDTO;
import com.ecommerce.orderservice.mapper.OrderItemMapper;
import com.ecommerce.orderservice.model.OrderItem;
import com.ecommerce.orderservice.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = OrderItemMapper.toEntity(orderItemDTO);
        OrderItem saved = orderItemRepository.save(orderItem);
        return OrderItemMapper.toDTO(saved);
    }

    public Optional<OrderItemDTO> getOrderItemById(Long id) {
        return orderItemRepository.findById(id).map(OrderItemMapper::toDTO);
    }

    public OrderItemDTO updateOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = OrderItemMapper.toEntity(orderItemDTO);
        OrderItem updated = orderItemRepository.save(orderItem);
        return OrderItemMapper.toDTO(updated);
    }

    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

    public Page<OrderItemDTO> listOrderItems(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortBy());
        PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        return orderItemRepository.findAll(pageRequest).map(OrderItemMapper::toDTO);
    }
} 