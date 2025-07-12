package com.ecommerce.notificationservice.mapper;

import com.ecommerce.notificationservice.dto.NotificationDTO;
import com.ecommerce.notificationservice.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    private NotificationMapper() {}
    public static NotificationDTO toDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setUserId(notification.getUserId());
        dto.setType(notification.getType());
        dto.setStatus(notification.getStatus());
        dto.setMessage(notification.getMessage());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setReadAt(notification.getReadAt());
        return dto;
    }

    public static Notification toEntity(NotificationDTO dto) {
        Notification notification = new Notification();
        notification.setId(dto.getId());
        notification.setUserId(dto.getUserId());
        notification.setType(dto.getType());
        notification.setStatus(dto.getStatus());
        notification.setMessage(dto.getMessage());
        notification.setCreatedAt(dto.getCreatedAt());
        notification.setReadAt(dto.getReadAt());
        return notification;
    }
} 