package com.ecommerce.orderservice.exception;

public class OrderCancellationException extends RuntimeException {
    
    public OrderCancellationException(String message) {
        super(message);
    }
    
    public OrderCancellationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public OrderCancellationException(Throwable cause) {
        super(cause);
    }
}
