package com.ecommerce.orderservice.consumer;

import com.ecommerce.orderservice.dto.cancel.CancelOrderRequestDTO;
import com.ecommerce.orderservice.service.OrderCancellationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCancellationConsumer {

    private final OrderCancellationService orderCancellationService;

    @KafkaListener(
        topics = "${kafka.topic.order-cancel-requests:order.cancel.requests}",
        groupId = "${spring.kafka.consumer.group-id:order-service}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeCancellationRequest(
            @Payload CancelOrderRequestDTO request,
            Acknowledgment ack) {
        
        log.info("Received order cancellation request: {}", request.getRequestId());
        
        try {
            orderCancellationService.processCancellationRequest(request);
            ack.acknowledge();
            log.info("Successfully processed cancellation request: {}", request.getRequestId());
        } catch (Exception e) {
            log.error("Error processing cancellation request: {}", request.getRequestId(), e);
            // The message will be retried based on the retry configuration
            throw e;
        }
    }
}
