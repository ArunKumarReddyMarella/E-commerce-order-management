package com.ecommerce.paymentservice.consumer;

import com.ecommerce.paymentservice.dto.refund.RefundRequestDTO;
import com.ecommerce.paymentservice.service.RefundProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Consumer for processing refund requests from Kafka.
 * Uses standard Spring Kafka consumption pattern without manual acknowledgment.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RefundRequestConsumer {

    private final RefundProcessingService refundProcessingService;

    @KafkaListener(
        topics = "${kafka.topic.payment-refund-requests:payment.refund.requests}",
        groupId = "${spring.kafka.consumer.group-id:payment-service}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    @Transactional
    public void consumeRefundRequest(@Payload RefundRequestDTO refundRequest) {
        try {
            if (refundRequest == null) {
                throw new IllegalArgumentException("Received null refund request");
            }
            
            log.info("Processing refund request: {}", refundRequest);
            
            // Validate required fields
            if (refundRequest.getRefundId() == null || refundRequest.getOrderId() == null || 
                refundRequest.getPaymentId() == null || refundRequest.getAmount() == null) {
                throw new IllegalArgumentException("Missing required fields in refund request: " + refundRequest);
            }
            
            // Set default currency if null
            if (refundRequest.getCurrency() == null) {
                refundRequest.setCurrency("USD"); // or your default currency
                log.debug("Set default currency for refund request: {}", refundRequest.getRefundId());
            }
            
            // Process the refund request
            refundProcessingService.processRefundRequest(refundRequest);
            
            log.info("Successfully processed refund request: {}", refundRequest.getRefundId());
        } catch (Exception e) {
            // Log the error with more context
            String errorMsg = String.format("Error processing refund request %s: %s", 
                refundRequest != null ? refundRequest.getRefundId() : "unknown", 
                e.getMessage());
            log.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }
}
