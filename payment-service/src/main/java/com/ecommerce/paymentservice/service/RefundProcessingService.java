package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.dto.refund.RefundRequestDTO;
import com.ecommerce.paymentservice.dto.refund.RefundResultDTO;
import com.ecommerce.paymentservice.exception.PaymentNotFoundException;
import com.ecommerce.paymentservice.exception.PaymentProcessingException;
import com.ecommerce.paymentservice.model.Payment;
import com.ecommerce.paymentservice.model.PaymentStatus;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundProcessingService {

    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${kafka.topic.payment-refund-results:payment.refund.results}")
    private String paymentRefundResultsTopic;

    /**
     * Process a refund request asynchronously
     */
    @Transactional
    public void processRefundRequest(RefundRequestDTO refundRequest) {
        log.info("Processing refund request: {}", refundRequest.getRefundId());
        
        try {
            // 1. Validate the payment exists and can be refunded
            Payment payment = validateAndGetPayment(refundRequest);
            
            // 2. Send processing status
            sendRefundStatus(RefundResultDTO.processing(
                    refundRequest.getRefundId(),
                    refundRequest.getCancelRequestId(),
                    refundRequest.getOrderId(),
                    refundRequest.getPaymentId(),
                    refundRequest.getAmount(),
                    refundRequest.getCurrency()
            ));
            
            // 3. Process the refund (simulated)
            processRefund(payment, refundRequest);
            
            // 4. Update payment status and send completion status
            payment.setStatus(PaymentStatus.REFUNDED.toString());
            paymentRepository.save(payment);
            
            String referenceNumber = "REF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            // Ensure all parameters match the expected types for the completed() method
            sendRefundStatus(RefundResultDTO.completed(
                    refundRequest.getRefundId().toString(), // Convert to String
                    refundRequest.getCancelRequestId().toString(), // Convert to String
                    refundRequest.getOrderId(), // Long
                    refundRequest.getPaymentId(), // Long
                    refundRequest.getAmount(),
                    refundRequest.getCurrency(),
                    referenceNumber
            ));
            
            log.info("Successfully processed refund: {}", refundRequest.getRefundId());
            
        } catch (Exception e) {
            log.error("Error processing refund: {}", refundRequest.getRefundId(), e);
            // Ensure all parameters match the expected types for the failed() method
            sendRefundStatus(RefundResultDTO.failed(
                    refundRequest.getRefundId().toString(), // Convert to String
                    refundRequest.getCancelRequestId().toString(), // Convert to String
                    refundRequest.getOrderId(), // Long
                    refundRequest.getPaymentId(), // Long
                    e.getMessage() // String
            ));
        }
    }
    
    private Payment  validateAndGetPayment(RefundRequestDTO refundRequest) {
        Payment payment = paymentRepository.findById(refundRequest.getPaymentId())
                .orElseThrow(() -> new PaymentNotFoundException(String.format("Payment not found with id: %s", refundRequest.getPaymentId())));
        
        // Validate payment can be refunded - compare the status string values
        if (!PaymentStatus.COMPLETED.toString().equals(payment.getStatus())) {
            throw new IllegalStateException("Payment is not in a refundable state: " + payment.getStatus());
        }
        
        // Validate refund amount doesn't exceed payment amount
        if (refundRequest.getAmount().compareTo(payment.getAmount()) > 0) {
            throw new IllegalArgumentException("Refund amount exceeds payment amount");
        }
        
        return payment;
    }
    
    private void processRefund(Payment payment, RefundRequestDTO refundRequest) {
        // In a real implementation, this would integrate with a payment gateway
        // For now, we'll simulate processing time
        try {
            Thread.sleep(1000); // Simulate processing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PaymentProcessingException("Refund processing was interrupted", e);
        }
        
        // Additional validation and processing logic would go here
        log.info("Processed refund for payment: {}", payment.getId());
    }
    
    private void sendRefundStatus(RefundResultDTO refundResult) {
        kafkaTemplate.send(
                paymentRefundResultsTopic,
                refundResult.getPaymentId().toString(),
                refundResult
        );
    }
}
