package com.ecommerce.notificationservice.mapper;

import com.ecommerce.notificationservice.dto.NotificationDTO;
import com.ecommerce.notificationservice.model.Notification;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Notification and NotificationDTO objects.
 * Includes null checks to prevent NullPointerExceptions.
 */
@Component
public class NotificationMapper {
    
    private NotificationMapper() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Converts a Notification entity to a NotificationDTO.
     *
     * @param notification the Notification to convert, can be null
     * @return the converted NotificationDTO, or null if the input is null
     */
    public static NotificationDTO toDTO(Notification notification) {
        if (notification == null) {
            return null;
        }
        
        return NotificationDTO.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .type(notification.getType())
                .status(notification.getStatus())
                .message(notification.getMessage())
                .createdAt(notification.getCreatedAt())
                .readAt(notification.getReadAt())
                .build();
    }

    /**
     * Converts a NotificationDTO to a Notification entity.
     *
     * @param dto the NotificationDTO to convert, can be null
     * @return the converted Notification, or null if the input is null
     */
    public static Notification toEntity(NotificationDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Notification.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .type(dto.getType())
                .status(dto.getStatus())
                .message(dto.getMessage())
                .createdAt(dto.getCreatedAt())
                .readAt(dto.getReadAt())
                .build();
    }
}