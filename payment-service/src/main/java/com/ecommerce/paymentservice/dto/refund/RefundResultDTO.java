package com.ecommerce.paymentservice.dto.refund;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefundResultDTO {
    public enum RefundStatus {
        PROCESSING,
        COMPLETED,
        FAILED,
        DECLINED
    }

    private String refundId;
    private String cancelRequestId;
    private Long orderId;
    private Long paymentId;
    private RefundStatus status;
    private BigDecimal amountRefunded;
    private String currency;
    private String referenceNumber;
    private String failureReason;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Instant processedAt;
    
    public static RefundResultDTO processing(String refundId, String cancelRequestId, Long orderId, Long paymentId, BigDecimal amount, String currency) {
        return RefundResultDTO.builder()
                .refundId(refundId)
                .cancelRequestId(cancelRequestId)
                .orderId(orderId)
                .paymentId(paymentId)
                .status(RefundStatus.PROCESSING)
                .amountRefunded(amount)
                .currency(currency)
                .processedAt(Instant.now())
                .build();
    }
    
    public static RefundResultDTO completed(String refundId, String cancelRequestId, Long orderId, Long paymentId, 
                                          BigDecimal amount, String currency, String referenceNumber) {
        return RefundResultDTO.builder()
                .refundId(refundId)
                .cancelRequestId(cancelRequestId)
                .orderId(orderId)
                .paymentId(paymentId)
                .status(RefundStatus.COMPLETED)
                .amountRefunded(amount)
                .currency(currency)
                .referenceNumber(referenceNumber)
                .processedAt(Instant.now())
                .build();
    }
    
    public static RefundResultDTO failed(String refundId, String cancelRequestId, Long orderId, Long paymentId, 
                                        String failureReason) {
        return RefundResultDTO.builder()
                .refundId(refundId)
                .cancelRequestId(cancelRequestId)
                .orderId(orderId)
                .paymentId(paymentId)
                .status(RefundStatus.FAILED)
                .failureReason(failureReason)
                .processedAt(Instant.now())
                .build();
    }
}
