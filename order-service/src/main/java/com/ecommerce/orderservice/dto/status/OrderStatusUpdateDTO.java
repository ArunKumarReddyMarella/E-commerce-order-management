package com.ecommerce.orderservice.dto.status;

import com.ecommerce.orderservice.dto.refund.RefundResultDTO;
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
public class OrderStatusUpdateDTO {
    private Long orderId;
    private OrderStatus status;
    private OrderStatus previousStatus;
    private RefundResultDTO.RefundStatus refundStatus;
    private String refundId;
    private BigDecimal refundAmount;
    private String message;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Instant timestamp;
    
    public static OrderStatusUpdateDTO create(
            Long orderId,
            OrderStatus status,
            RefundResultDTO.RefundStatus refundStatus,
            String message) {
        
        return OrderStatusUpdateDTO.builder()
                .orderId(orderId)
                .status(status)
                .refundStatus(refundStatus)
                .message(message)
                .timestamp(Instant.now())
                .build();
    }
}
