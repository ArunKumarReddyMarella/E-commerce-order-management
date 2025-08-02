package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.cancel.CancelOrderRequestDTO;
import com.ecommerce.orderservice.dto.cancel.CancelOrderResponseDTO;
import com.ecommerce.orderservice.service.OrderCancellationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderCancellationController {

    private final OrderCancellationService orderCancellationService;

    @PostMapping("/{orderId}/cancel")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CancelOrderResponseDTO requestOrderCancellation(
            @PathVariable Long orderId,
            @RequestParam(required = false) String reason,
            @RequestParam(defaultValue = "true") boolean requestRefund) {
        
        // In a real application, get user ID from security context
        Long userId = 0L;
        
        // Create cancellation request
        CancelOrderRequestDTO request = CancelOrderRequestDTO.create(
                orderId,
                userId,
                reason != null ? reason : "Customer requested cancellation",
                requestRefund
        );
        
        log.info("Received cancellation request: {}", request);
        
        // In a real application, you might publish to Kafka here
        // For testing, we'll process it directly
        orderCancellationService.processCancellationRequest(request);
        
        return CancelOrderResponseDTO.accepted(request.getRequestId());
    }
}
