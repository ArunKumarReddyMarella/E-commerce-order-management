package com.ecommerce.orderservice.dto.refund;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundRequestDTO {
    private String refundId;
    private String cancelRequestId;
    private Long orderId;
    private Long paymentId;
    private BigDecimal amount;
    private String currency;
    private String reason;
    private String initiatedBy;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Instant requestedAt;
    
    public static RefundRequestDTO create(
            String cancelRequestId,
            Long orderId,
            Long paymentId,
            BigDecimal amount,
            String currency,
            String reason,
            String initiatedBy) {
        
        return RefundRequestDTO.builder()
                .refundId(UUID.randomUUID().toString())
                .cancelRequestId(cancelRequestId)
                .orderId(orderId)
                .paymentId(paymentId)
                .amount(amount)
                .currency(currency)
                .reason(reason)
                .initiatedBy(initiatedBy)
                .requestedAt(Instant.now())
                .build();
    }
}
