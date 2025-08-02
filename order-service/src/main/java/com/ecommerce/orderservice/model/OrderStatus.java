package com.ecommerce.orderservice.model;

public enum OrderStatus {
    // Initial state when order is created
    CREATED,
    
    // Payment related statuses
    PAYMENT_PENDING,
    PAYMENT_COMPLETED,
    PAYMENT_FAILED,
    
    // Order processing statuses
    PROCESSING,
    SHIPPED,
    DELIVERED,
    
    // Cancellation related statuses
    CANCELLATION_REQUESTED,
    CANCELLATION_IN_PROGRESS,
    CANCELLATION_APPROVED,
    CANCELLATION_REJECTED,
    CANCELLATION_FAILED,
    CANCELLED,
    
    // Refund related statuses
    REFUND_INITIATED,
    REFUND_COMPLETED,  // Payment service has completed the refund
    REFUND_FAILED,     // Refund processing failed
    REFUNDED,          // Order has been refunded (legacy status, prefer REFUND_COMPLETED)
    REFUND_APPROVED,   // Refund has been approved by the system
    
    // Final states
    COMPLETED,
    REJECTED;
    
    /**
     * Check if the order can be cancelled in its current state
     */
    public boolean isCancellable() {
        return this == CREATED || 
               this == PAYMENT_COMPLETED || 
               this == PROCESSING || 
               this == CANCELLATION_REQUESTED;
    }
}
