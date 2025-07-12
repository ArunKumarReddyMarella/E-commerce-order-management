package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderDTO;
import com.ecommerce.orderservice.dto.PageRequestDTO;
import com.ecommerce.orderservice.mapper.OrderMapper;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = OrderMapper.toEntity(orderDTO);
        Order saved = orderRepository.save(order);
        return OrderMapper.toDTO(saved);
    }

    public Optional<OrderDTO> getOrderById(Long id) {
        return orderRepository.findById(id).map(OrderMapper::toDTO);
    }

    public OrderDTO updateOrder(OrderDTO orderDTO) {
        Order order = OrderMapper.toEntity(orderDTO);
        Order updated = orderRepository.save(order);
        return OrderMapper.toDTO(updated);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Page<OrderDTO> listOrders(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortBy());
        PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        return orderRepository.findAll(pageRequest).map(OrderMapper::toDTO);
    }
} 