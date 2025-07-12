package com.ecommerce.paymentservice.repository;

import com.ecommerce.paymentservice.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
} 