package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.NotificationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${kafka.topic.notifications:notifications}")
    private String topic;

    public void sendNotification(NotificationDTO notification) {
        try {
            log.info("Sending notification: {}", notification);
            kafkaTemplate.send(topic, String.valueOf(notification.getUserId()), notification);
        } catch (Exception e) {
            log.error("Error sending notification: {}", e.getMessage());
            throw new RuntimeException("Failed to send notification", e);
        }
    }
}
