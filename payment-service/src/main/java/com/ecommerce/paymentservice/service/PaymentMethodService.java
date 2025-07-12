package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.dto.PageRequestDTO;
import com.ecommerce.paymentservice.dto.PaymentMethodDTO;
import com.ecommerce.paymentservice.mapper.PaymentMethodMapper;
import com.ecommerce.paymentservice.model.PaymentMethod;
import com.ecommerce.paymentservice.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodDTO createPaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethod = PaymentMethodMapper.toEntity(paymentMethodDTO);
        PaymentMethod saved = paymentMethodRepository.save(paymentMethod);
        return PaymentMethodMapper.toDTO(saved);
    }

    public Optional<PaymentMethodDTO> getPaymentMethodById(Long id) {
        return paymentMethodRepository.findById(id).map(PaymentMethodMapper::toDTO);
    }

    public PaymentMethodDTO updatePaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethod = PaymentMethodMapper.toEntity(paymentMethodDTO);
        PaymentMethod updated = paymentMethodRepository.save(paymentMethod);
        return PaymentMethodMapper.toDTO(updated);
    }

    public void deletePaymentMethod(Long id) {
        paymentMethodRepository.deleteById(id);
    }

    public List<PaymentMethodDTO> listPaymentMethods() {
        return paymentMethodRepository.findAll().stream().map(PaymentMethodMapper::toDTO).collect(Collectors.toList());
    }

    public Page<PaymentMethodDTO> listPaymentMethodsPaginated(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortBy());
        Pageable pageable = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        return paymentMethodRepository.findAll(pageable).map(PaymentMethodMapper::toDTO);
    }
} 