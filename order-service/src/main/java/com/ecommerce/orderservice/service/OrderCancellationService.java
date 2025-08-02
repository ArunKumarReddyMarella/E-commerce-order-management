package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.cancel.CancelOrderRequestDTO;
import com.ecommerce.orderservice.dto.refund.RefundRequestDTO;
import com.ecommerce.orderservice.exception.OrderCancellationException;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderStatus;
import com.ecommerce.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCancellationService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${kafka.topic.order-cancel-requests:order.cancel.requests}")
    private String orderCancelRequestsTopic;
    
    @Value("${kafka.topic.payment-refund-requests:payment.refund.requests}")
    private String paymentRefundRequestsTopic;

    /**
     * Initiates an async order cancellation process
     */
    @Transactional
    public void processCancellationRequest(CancelOrderRequestDTO request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new OrderCancellationException("Order not found with id: " + request.getOrderId()));

        //TODO: This is for testing and can be removed after implementing security and auditing
        request.setUserId(order.getUserId());
        // Validate order can be cancelled
        validateOrderCanBeCancelled(order, request.getUserId());

        // Update order status to CANCELLATION_IN_PROGRESS
        order.setOrderStatus(OrderStatus.CANCELLATION_IN_PROGRESS);
        order = orderRepository.save(order);

        try {
            // If refund is requested and payment exists, initiate refund
            if (request.isRequestRefund() && order.getPaymentId() != null) {
                initiateRefund(order, request);
            } else {
                // If no refund needed, complete cancellation
                completeOrderCancellation(order, request.getRequestId());
            }
        } catch (Exception e) {
            log.error("Error processing cancellation request: {}", request.getRequestId(), e);
            handleCancellationFailure(order, request.getRequestId(), e);
            throw e;
        }
    }

    private void validateOrderCanBeCancelled(Order order, Long userId) {
        // Verify user has permission to cancel this order
        if (userId != null && !order.getUserId().equals(userId)) {
            throw new OrderCancellationException("User is not authorized to cancel this order");
        }

        // Check if order is in a cancellable state
        if (!order.getOrderStatus().isCancellable()) {
            throw new OrderCancellationException(
                    String.format("Order %d cannot be cancelled in its current state: %s",
                            order.getId(), order.getOrderStatus()));
        }
    }

    private void initiateRefund(Order order, CancelOrderRequestDTO request) {
        // Create and send refund request
        RefundRequestDTO refundRequest = RefundRequestDTO.create(
                request.getRequestId(),
                order.getId(),
                order.getPaymentId(),
                order.getTotalAmount(),
                order.getCurrency(),
                request.getReason(),
                "system" // or get actual user ID from security context
        );

        log.info("Initiating refund for order {}: {}", order.getId(), refundRequest);
        kafkaTemplate.send(paymentRefundRequestsTopic, order.getPaymentId().toString(), refundRequest);
    }

    @Transactional
    public void completeOrderCancellation(Order order, String requestId) {
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        log.info("Order {} cancellation completed successfully. Request ID: {}", order.getId(), requestId);
        
        // TODO: Send notification
    }

    @Transactional
    public void handleCancellationFailure(Order order, String requestId, Throwable cause) {
        order.setOrderStatus(OrderStatus.CANCELLATION_FAILED);
        orderRepository.save(order);
        log.error("Order {} cancellation failed. Request ID: {}", order.getId(), requestId, cause);
        
        // TODO: Send failure notification
    }
}
