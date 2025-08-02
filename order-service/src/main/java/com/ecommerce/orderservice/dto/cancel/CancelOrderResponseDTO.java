package com.ecommerce.orderservice.dto.cancel;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelOrderResponseDTO {
    private String requestId;
    private String status;
    private String message;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Instant timestamp;
    
    public static CancelOrderResponseDTO accepted(String requestId) {
        return CancelOrderResponseDTO.builder()
                .requestId(requestId)
                .status("ACCEPTED")
                .message("Order cancellation request has been received and is being processed")
                .timestamp(Instant.now())
                .build();
    }
    
    public static CancelOrderResponseDTO error(String requestId, String errorMessage) {
        return CancelOrderResponseDTO.builder()
                .requestId(requestId)
                .status("ERROR")
                .message(errorMessage)
                .timestamp(Instant.now())
                .build();
    }
}
