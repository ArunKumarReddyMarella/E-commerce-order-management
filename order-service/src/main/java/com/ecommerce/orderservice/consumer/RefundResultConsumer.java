package com.ecommerce.orderservice.consumer;

import com.ecommerce.orderservice.dto.refund.RefundResultDTO;
import com.ecommerce.orderservice.service.OrderRefundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefundResultConsumer {

    private final OrderRefundService orderRefundService;

    @KafkaListener(
        topics = "${kafka.topic.payment-refund-results:payment.refund.results}",
        groupId = "${spring.kafka.consumer.group-id:order-service}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeRefundResult(@Payload(required = false) RefundResultDTO refundResult) {
        
        log.info("Received refund result: {}", refundResult.getRefundId());
        
        try {
            orderRefundService.handleRefundResult(refundResult);
            log.info("Successfully processed refund result: {}", refundResult.getRefundId());
        } catch (Exception e) {
            log.error("Error processing refund result: {}", refundResult.getRefundId(), e);
            // The message will be retried based on the retry configuration
            throw e;
        }
    }
}
