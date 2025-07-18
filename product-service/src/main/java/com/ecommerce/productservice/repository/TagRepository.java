package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
} 