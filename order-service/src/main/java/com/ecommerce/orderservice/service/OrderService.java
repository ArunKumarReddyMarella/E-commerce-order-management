package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.*;
import com.ecommerce.orderservice.mapper.OrderItemMapper;
import com.ecommerce.orderservice.mapper.OrderMapper;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderItem;
import com.ecommerce.orderservice.repository.OrderItemRepository;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.client.UserServiceClient;
import com.ecommerce.orderservice.client.ProductServiceClient;
import com.ecommerce.orderservice.client.PaymentServiceClient;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ecommerce.orderservice.model.OrderStatus;
import com.ecommerce.orderservice.dto.CancelOrderRequest;
import com.ecommerce.orderservice.dto.NotificationDTO;
import com.ecommerce.orderservice.dto.RefundDTO;
import com.ecommerce.orderservice.exception.OrderCancellationException;

@Slf4j
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private ProductServiceClient productServiceClient;
    @Autowired
    private PaymentServiceClient paymentServiceClient;
    @Autowired
    private NotificationProducerService notificationProducerService;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public OrderDTO createOrder(OrderRequest orderRequest) {
        // 1. Verify user exists and is active
        boolean isUserValid = userServiceClient.verifyUser(orderRequest.getUserId());
        if (!isUserValid) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "User not found or inactive. Cannot create order."
            );
        }
        
        // 2. Get user details for order processing
        UserDTO user = userServiceClient.getUserById(orderRequest.getUserId());
        if (user == null) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Failed to fetch user details after verification"
            );
        }
        
        // 2. Validate shipping address
        if (orderRequest.getShippingAddress() == null) {
            throw new RuntimeException("Shipping address is required");
        }
        
        // In a real implementation, you might want to validate the address against a service
        // For now, we'll just ensure the required fields are present
        AddressDTO shippingAddress = orderRequest.getShippingAddress();
        if (shippingAddress.getAddress() == null || shippingAddress.getCity() == null || 
            shippingAddress.getState() == null || shippingAddress.getPostalCode() == null || 
            shippingAddress.getCountry() == null) {
            throw new RuntimeException("Invalid shipping address. Please provide all required address fields.");
        }
        
        // 2. Validate products and calculate total amount
        BigDecimal calculatedTotal = BigDecimal.ZERO;
        for (OrderItemRequest itemRequest : orderRequest.getOrderItems()) {
            // In a real scenario, you would fetch product details including price from the product service
             ProductDTO product = productServiceClient.getProductById(itemRequest.getProductId());
             if (product == null) {
                 throw new RuntimeException("Product not found: " + itemRequest.getProductId());
             }
             if(product.getStock() < itemRequest.getQuantity()) {
                 throw new RuntimeException("Insufficient stock for product: " + product.getTitle());
             }
             calculatedTotal = calculatedTotal.add(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
        }
        
        // 3. Save the order
        Order order = OrderMapper.toEntity(orderRequest);
        // Set the address ID from the DTO (or save the address and get its ID)
        // In a real implementation, you might want to save the address if it's new
        // and then set the address ID on the order
        order.setAddressId(orderRequest.getShippingAddress().getId());
        
        // Override total with calculated total if needed
        order.setTotalAmount(calculatedTotal);
        Order savedOrder = orderRepository.save(order);
        
        // 4. Save order items
        List<OrderItem> orderItems = orderRequest.getOrderItems().stream()
                .map(itemRequest -> {
                    OrderItem item = OrderItemMapper.toEntity(itemRequest, savedOrder.getId());
                    // Set price from product service in a real scenario
                     item.setPrice(productServiceClient.getProductById(item.getProductId()).getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
                    return item;
                })
                .collect(Collectors.toList());
        
        orderItemRepository.saveAll(orderItems);
        
        // 3. Process order items (if any)
        // Note: In a real implementation, you would process order items here
        // and calculate the total amount based on the items
        
        // 5. Process payment (commented out for now)
         PaymentDTO payment = new PaymentDTO();
         payment.setOrderId(savedOrder.getId());
         payment.setAmount(savedOrder.getTotalAmount());
         payment.setUserId(savedOrder.getUserId());
         payment.setStatus("COMPLETED");
         payment.setPaymentMethod("CREDIT_CARD");
         PaymentDTO paymentResponse = paymentServiceClient.processPayment(payment);
         savedOrder.setPaymentId(paymentResponse.getId());

        // 6. Update order status based on payment
         if ("COMPLETED".equals(paymentResponse.getStatus())) {
             savedOrder.setOrderStatus(OrderStatus.PAYMENT_COMPLETED);
         } else {
             savedOrder.setOrderStatus(OrderStatus.PAYMENT_FAILED);
         }
        // reduce the stock of the products
         for (OrderItem item : orderItems) {
             ProductDTO product = productServiceClient.getProductById(item.getProductId());
             product.setStock(product.getStock() - item.getQuantity());
             productServiceClient.updateProduct(item.getProductId(), product);
         }
        // 7. Save the updated order
         Order updatedOrder = orderRepository.save(savedOrder);

        // 8. Send notification via Kafka
        try {
            NotificationDTO notification = NotificationDTO.builder()
                    .userId(updatedOrder.getUserId())
                    .type("ORDER_STATUS")
                    .status(updatedOrder.getOrderStatus().name())
                    .message("Your order #" + updatedOrder.getId() + " has been " + updatedOrder.getOrderStatus())
                    .createdAt(Instant.now())
                    .readAt(Instant.now())
                    .build();
            
            notificationProducerService.sendNotification(notification);
            log.info("Notification queued for order: {}", updatedOrder.getId());
        } catch (Exception e) {
            log.error("Failed to send notification for order {}: {}", updatedOrder.getId(), e.getMessage());
            // Continue with order processing even if notification fails
        }
        return OrderMapper.toDTO(savedOrder);
    }

    public Optional<OrderDTO> getOrderById(Long id) {
        return orderRepository.findById(id).map(OrderMapper::toDTO);
    }

    @Transactional
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        // Fetch the existing order
        Order existingOrder = orderRepository.findById(orderDTO.getId())
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderDTO.getId()));
        
        // Merge the DTO data with the existing order
        Order updatedOrder = OrderMapper.mergeWithDTO(existingOrder, orderDTO);
        
        // Save and return the updated order
        Order savedOrder = orderRepository.save(updatedOrder);
        return OrderMapper.toDTO(savedOrder);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Page<OrderDTO> listOrdersPaginated(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortBy());
        Pageable pageable = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        return orderRepository.findAll(pageable).map(OrderMapper::toDTO);
    }

    @Transactional
    public OrderDTO cancelOrder(CancelOrderRequest cancelRequest) {
        // 1. Validate and fetch the order
        Order order = orderRepository.findById(cancelRequest.getOrderId())
            .orElseThrow(() -> new OrderCancellationException("Order not found with id: " + cancelRequest.getOrderId()));

        // 2. Validate user has permission to cancel this order
        if (cancelRequest.getUserId() != null && !order.getUserId().equals(cancelRequest.getUserId())) {
            throw new OrderCancellationException("User is not authorized to cancel this order");
        }

        // 3. Check if order can be cancelled
        if (!isOrderCancellable(order)) {
            throw new OrderCancellationException("Order cannot be cancelled in its current state: " + order.getOrderStatus());
        }

        // 4. Update order status to CANCELLATION_REQUESTED
        order.setOrderStatus(OrderStatus.CANCELLATION_REQUESTED);
        order = orderRepository.save(order);

        try {
            // 5. Process refund if payment was made and refund is requested
            if (cancelRequest.isRequestRefund()) {
                processRefund(order, cancelRequest.getReason());
            }

            // 6. Update order status to CANCELLED
            order.setOrderStatus(OrderStatus.CANCELLED);
            order = orderRepository.save(order);

            // 7. Send notification
            sendCancellationNotification(order, cancelRequest.getReason());

            return OrderMapper.toDTO(order);
        } catch (Exception e) {
            // If anything fails, mark the order as CANCELLATION_FAILED
            order.setOrderStatus(OrderStatus.CANCELLATION_FAILED);
            order = orderRepository.save(order);
            throw new OrderCancellationException("Failed to cancel order: " + e.getMessage(), e);
        }
    }

    private boolean isOrderCancellable(Order order) {
        // Define which statuses allow cancellation
        return order.getOrderStatus() == OrderStatus.CREATED ||
               order.getOrderStatus() == OrderStatus.PAYMENT_COMPLETED ||
               order.getOrderStatus() == OrderStatus.PROCESSING;
    }

    private void processRefund(Order order, String reason) {
        // This method is now handled asynchronously via Kafka
        log.info("Refund processing for order {} will be handled asynchronously via Kafka", order.getId());
    }

    private void sendCancellationNotification(Order order, String reason) {
        try {
            NotificationDTO notification = NotificationDTO.builder()
                .userId(order.getUserId())
                .type("ORDER_CANCELLATION")
                .status(order.getOrderStatus().name())
                .message(String.format("Your order #%d has been cancelled. Reason: %s", 
                    order.getId(), reason))
                .createdAt(Instant.now())
                .build();

            notificationProducerService.sendNotification(notification);
        } catch (Exception e) {
            log.error("Failed to send cancellation notification for order {}: {}", 
                order.getId(), e.getMessage());
            // Don't fail the cancellation if notification fails
        }
    }
}