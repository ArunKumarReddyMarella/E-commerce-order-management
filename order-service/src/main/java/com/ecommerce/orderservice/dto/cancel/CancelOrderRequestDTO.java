package com.ecommerce.orderservice.dto.cancel;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelOrderRequestDTO {
    private String requestId;
    private Long orderId;
    private Long userId;
    private String reason;
    private boolean requestRefund = true;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Instant requestedAt;
    
    public static CancelOrderRequestDTO create(Long orderId, Long userId, String reason, boolean requestRefund) {
        return CancelOrderRequestDTO.builder()
                .requestId(UUID.randomUUID().toString())
                .orderId(orderId)
                .userId(userId)
                .reason(reason)
                .requestRefund(requestRefund)
                .requestedAt(Instant.now())
                .build();
    }
}
