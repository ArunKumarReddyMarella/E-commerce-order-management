package com.ecommerce.orderservice.dto.notification;

import com.ecommerce.orderservice.model.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDTO {
    private Long orderId;
//    private String recipientEmail;
//    private String recipientName;
    private String subject;
    private String message;
    private OrderStatus orderStatus;
    private String refundId;
    private BigDecimal refundAmount;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Instant timestamp;
    
    public static NotificationRequestDTO fromStatusUpdate(
            Long orderId,
//            String recipientEmail,
//            String recipientName,
            OrderStatus status,
            String refundId,
            BigDecimal refundAmount,
            String message) {
        
        String subject = "Order #" + orderId + " - " + status.name();
        
        return NotificationRequestDTO.builder()
                .orderId(orderId)
//                .recipientEmail(recipientEmail)
//                .recipientName(recipientName)
                .subject(subject)
                .message(message)
                .orderStatus(status)
                .refundId(refundId)
                .refundAmount(refundAmount)
                .timestamp(Instant.now())
                .build();
    }
}
