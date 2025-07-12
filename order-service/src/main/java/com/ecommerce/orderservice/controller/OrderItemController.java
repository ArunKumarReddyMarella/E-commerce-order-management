package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.OrderItemDTO;
import com.ecommerce.orderservice.dto.PageRequestDTO;
import com.ecommerce.orderservice.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<OrderItemDTO> createOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
        return ResponseEntity.ok(orderItemService.createOrderItem(orderItemDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable Long id) {
        Optional<OrderItemDTO> orderItemOpt = orderItemService.getOrderItemById(id);
        return orderItemOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemDTO> updateOrderItem(@PathVariable Long id, @RequestBody OrderItemDTO orderItemDTO) {
        orderItemDTO.setId(id);
        return ResponseEntity.ok(orderItemService.updateOrderItem(orderItemDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/list")
    public Page<OrderItemDTO> listOrderItems(@RequestBody PageRequestDTO pageRequestDTO) {
        return orderItemService.listOrderItems(pageRequestDTO);
    }
} 