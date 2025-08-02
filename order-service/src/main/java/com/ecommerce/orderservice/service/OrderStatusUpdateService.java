package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.refund.RefundResultDTO;
import com.ecommerce.orderservice.dto.status.OrderStatusUpdateDTO;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderStatus;
import com.ecommerce.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderStatusUpdateService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${kafka.topic.order-status-updates:order.status.updates}")
    private String orderStatusUpdatesTopic;

    /**
     * Update order status and broadcast the update
     */
    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus newStatus, String message) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        
        OrderStatus previousStatus = order.getOrderStatus();
        order.setOrderStatus(newStatus);
        order = orderRepository.save(order);
        
        log.info("Updated order {} status from {} to {}", orderId, previousStatus, newStatus);
        
        // Broadcast the status update
        broadcastStatusUpdate(order, previousStatus, message);
    }
    
    /**
     * Broadcast order status update to interested services
     */
    private void broadcastStatusUpdate(Order order, OrderStatus previousStatus, String message) {
        OrderStatusUpdateDTO statusUpdate = OrderStatusUpdateDTO.builder()
                .orderId(order.getId())
                .status(order.getOrderStatus())
                .previousStatus(previousStatus)
                .message(message)
                .timestamp(Instant.now())
                .build();
        
        kafkaTemplate.send(
                orderStatusUpdatesTopic,
                order.getId().toString(),
                statusUpdate
        );
        
        log.debug("Broadcasted status update for order {}: {}", order.getId(), statusUpdate);
    }
    
    /**
     * Handle refund status updates and update order accordingly
     */
    @Transactional
    public void handleRefundStatusUpdate(RefundResultDTO refundResult) {
        Order order = orderRepository.findById(refundResult.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + refundResult.getOrderId()));
        
        OrderStatus newStatus;
        String message;
        
        switch (refundResult.getStatus()) {
            case COMPLETED:
                newStatus = OrderStatus.REFUNDED;
                message = String.format("Refund completed. Reference: %s", 
                        refundResult.getReferenceNumber());
                order.setRefundReference(refundResult.getReferenceNumber());
                order.setRefundAmount(refundResult.getAmountRefunded());
                break;
                
            case FAILED:
            case DECLINED:
                newStatus = OrderStatus.REFUND_FAILED;
                message = String.format("Refund failed: %s", 
                        refundResult.getFailureReason());
                order.setRefundFailureReason(refundResult.getFailureReason());
                break;
                
            case PROCESSING:
            default:
                // Don't change order status, just log
                log.info("Refund in progress for order {}: {}", 
                        order.getId(), refundResult.getStatus());
                return;
        }
        
        order.setOrderStatus(newStatus);
        orderRepository.save(order);
        
        // Broadcast the status update
        broadcastStatusUpdate(order, order.getOrderStatus(), message);
    }
}
