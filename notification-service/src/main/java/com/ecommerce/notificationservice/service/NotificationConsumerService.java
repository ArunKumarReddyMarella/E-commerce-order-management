package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.dto.NotificationDTO;
import com.ecommerce.notificationservice.mapper.NotificationMapper;
import com.ecommerce.notificationservice.model.Notification;
import com.ecommerce.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationConsumerService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @KafkaListener(
        topics = "${kafka.topic.notifications:notifications}",
        groupId = "${spring.kafka.consumer.group-id:notification-group}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    @Transactional
    public void consumeNotification(@Payload NotificationDTO notificationDTO) {
        try {
            log.info("Received notification for user {}: {}", 
                    notificationDTO.getUserId(), 
                    notificationDTO.getMessage());
            
            // Convert DTO to entity and save to database
            Notification notification = notificationMapper.toEntity(notificationDTO);
            notificationRepository.save(notification);
            
            log.info("Successfully processed notification with ID: {}", notification.getId());
            
            // Here you could add additional notification delivery logic (email, SMS, push, etc.)
            // For example: notificationDeliveryService.sendEmail(notification);
            
        } catch (Exception e) {
            log.error("Error processing notification: {}", e.getMessage(), e);
            // In a production environment, you might want to implement a retry mechanism or dead-letter queue
            throw new RuntimeException("Failed to process notification", e);
        }
    }
}
