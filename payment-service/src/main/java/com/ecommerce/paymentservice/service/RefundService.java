package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.dto.PageRequestDTO;
import com.ecommerce.paymentservice.dto.RefundDTO;
import com.ecommerce.paymentservice.mapper.RefundMapper;
import com.ecommerce.paymentservice.model.Refund;
import com.ecommerce.paymentservice.repository.RefundRepository;
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
public class RefundService {
    @Autowired
    private RefundRepository refundRepository;

    public RefundDTO createRefund(RefundDTO refundDTO) {
        Refund refund = RefundMapper.toEntity(refundDTO);
        Refund saved = refundRepository.save(refund);
        return RefundMapper.toDTO(saved);
    }

    public Optional<RefundDTO> getRefundById(Long id) {
        return refundRepository.findById(id).map(RefundMapper::toDTO);
    }

    public RefundDTO updateRefund(RefundDTO refundDTO) {
        Refund refund = RefundMapper.toEntity(refundDTO);
        Refund updated = refundRepository.save(refund);
        return RefundMapper.toDTO(updated);
    }

    public void deleteRefund(Long id) {
        refundRepository.deleteById(id);
    }

    public List<RefundDTO> listRefunds() {
        return refundRepository.findAll().stream().map(RefundMapper::toDTO).collect(Collectors.toList());
    }

    public Page<RefundDTO> listRefundsPaginated(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortBy());
        Pageable pageable = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        return refundRepository.findAll(pageable).map(RefundMapper::toDTO);
    }
} 