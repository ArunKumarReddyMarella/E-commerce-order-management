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

    /**
     * Merges non-null fields from NotificationDTO into an existing Notification entity
     * @param notification The existing notification to be updated
     * @param dto The DTO containing updated values
     * @return The updated Notification entity
     */
    public static Notification mergeWithDTO(Notification notification, NotificationDTO dto) {
        if (dto == null || notification == null) {
            return notification;
        }
        
        if (dto.getType() != null) {
            notification.setType(dto.getType());
        }
        if (dto.getStatus() != null) {
            notification.setStatus(dto.getStatus());
        }
        if (dto.getMessage() != null) {
            notification.setMessage(dto.getMessage());
        }
        if (dto.getReadAt() != null) {
            notification.setReadAt(dto.getReadAt());
        }
        // Note: id, userId, and createdAt are typically not updated via merge
        
        return notification;
    }
}