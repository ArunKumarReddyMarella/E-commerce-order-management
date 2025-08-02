package com.ecommerce.orderservice.consumer;

import com.ecommerce.orderservice.dto.NotificationDTO;
import com.ecommerce.orderservice.dto.notification.NotificationRequestDTO;
import com.ecommerce.orderservice.dto.status.OrderStatusUpdateDTO;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.NotificationProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderStatusUpdateConsumer {

    private final OrderRepository orderRepository;
    private final NotificationProducerService notificationProducerService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${kafka.topic.notifications:notifications}")
    private String notificationsTopic;

    @KafkaListener(
        topics = "${kafka.topic.order-status-updates:order.status.updates}",
        groupId = "${spring.kafka.consumer.group-id:order-service}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    @Transactional
    public void consumeOrderStatusUpdate(@Payload OrderStatusUpdateDTO statusUpdate) {
        try {
            log.info("Processing order status update: {}", statusUpdate);
            
            // Get order details for notification
            Optional<Order> orderOpt = orderRepository.findById(statusUpdate.getOrderId());
            if (orderOpt.isEmpty()) {
                log.warn("Order not found for status update: {}", statusUpdate.getOrderId());
                return;
            }
            
            Order order = orderOpt.get();

            NotificationDTO notification = NotificationDTO.builder()
                    .userId(order.getUserId())
                    .type("ORDER_STATUS_UPDATE")
                    .status(order.getOrderStatus().name())
                    .message(statusUpdate.getMessage())
                    .createdAt(Instant.now())
                    .build();
            
            // Publish notification event to Kafka
            notificationProducerService.sendNotification(notification);
            
            log.info("Successfully processed order status update for order: {}", order.getId());
            
        } catch (Exception e) {
            log.error("Error processing order status update: {}", statusUpdate, e);
            throw e; // Let the container handle retries
        }
    }
}
