package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.dto.PageRequestDTO;
import com.ecommerce.paymentservice.dto.PaymentDTO;
import com.ecommerce.paymentservice.mapper.PaymentMapper;
import com.ecommerce.paymentservice.model.Payment;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payment payment = PaymentMapper.toEntity(paymentDTO);
        Payment saved = paymentRepository.save(payment);
        return PaymentMapper.toDTO(saved);
    }

    public Optional<PaymentDTO> getPaymentById(Long id) {
        return paymentRepository.findById(id).map(PaymentMapper::toDTO);
    }

    @Transactional
    public PaymentDTO updatePayment(PaymentDTO paymentDTO) {
        // Fetch the existing payment
        Payment existingPayment = paymentRepository.findById(paymentDTO.getId())
            .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentDTO.getId()));
        
        // Merge the DTO data with the existing payment
        Payment updatedPayment = PaymentMapper.mergeWithDTO(existingPayment, paymentDTO);
        
        // Save and return the updated payment
        Payment savedPayment = paymentRepository.save(updatedPayment);
        return PaymentMapper.toDTO(savedPayment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public List<PaymentDTO> listPayments() {
        return paymentRepository.findAll().stream().map(PaymentMapper::toDTO).collect(Collectors.toList());
    }

    public Page<PaymentDTO> listPaymentsPaginated(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortBy());
        Pageable pageable = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        return paymentRepository.findAll(pageable).map(PaymentMapper::toDTO);
    }
} 