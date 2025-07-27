package com.ecommerce.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class  NotificationDTO {
    private Long id;
    private Long userId;
    private String type;
    private String status;
    private String message;
    private Instant createdAt;
    private Instant readAt;
} 