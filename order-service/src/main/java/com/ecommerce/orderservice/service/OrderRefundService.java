package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.refund.RefundResultDTO;
import com.ecommerce.orderservice.dto.status.OrderStatusUpdateDTO;
import com.ecommerce.orderservice.exception.OrderNotFoundException;
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
public class OrderRefundService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${kafka.topic.payment-refund-results:payment.refund.results}")
    private String paymentRefundResultsTopic;
    
    @Value("${kafka.topic.order-status-updates:order.status.updates}")
    private String orderStatusUpdatesTopic;

    /**
     * Handles refund results from the payment service
     */
    @Transactional
    public void handleRefundResult(RefundResultDTO refundResult) {
        log.info("Processing refund result: {}", refundResult.getRefundId());
        
        Order order = orderRepository.findById(refundResult.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(refundResult.getOrderId()));
        
        try {
            switch (refundResult.getStatus()) {
                case COMPLETED:
                    handleSuccessfulRefund(order, refundResult);
                    break;
                case FAILED:
                case DECLINED:
                    handleFailedRefund(order, refundResult);
                    break;
                case PROCESSING:
                    log.info("Refund is still processing for order: {}", order.getId());
                    break;
            }
            
            // Send status update
            sendOrderStatusUpdate(order, refundResult);
            
        } catch (Exception e) {
            log.error("Error processing refund result: {}", refundResult.getRefundId(), e);
            throw e;
        }
    }
    
    private void handleSuccessfulRefund(Order order, RefundResultDTO refundResult) {
        log.info("Refund completed successfully for order: {}", order.getId());
        order.setOrderStatus(OrderStatus.REFUNDED);
        order.setRefundReference(refundResult.getReferenceNumber());
        order.setRefundAmount(refundResult.getAmountRefunded());
        orderRepository.save(order);
        
        // TODO: Send refund completion notification
    }
    
    private void handleFailedRefund(Order order, RefundResultDTO refundResult) {
        log.warn("Refund failed for order: {}. Reason: {}", 
                order.getId(), refundResult.getFailureReason());
        
        order.setOrderStatus(OrderStatus.REFUND_FAILED);
        order.setRefundFailureReason(refundResult.getFailureReason());
        orderRepository.save(order);
        
        // TODO: Send refund failure notification
    }
    
    private void sendOrderStatusUpdate(Order order, RefundResultDTO refundResult) {
        // Create a status update DTO with relevant information
        OrderStatusUpdateDTO statusUpdate = OrderStatusUpdateDTO.builder()
                .orderId(order.getId())
                .status(order.getOrderStatus())
                .refundStatus(refundResult.getStatus())
                .refundId(refundResult.getRefundId())
                .refundAmount(refundResult.getAmountRefunded())
                .message("Refund " + refundResult.getStatus().name().toLowerCase())
                .timestamp(Instant.now())
                .build();
        
        kafkaTemplate.send(orderStatusUpdatesTopic, order.getId().toString(), statusUpdate);
    }
}
