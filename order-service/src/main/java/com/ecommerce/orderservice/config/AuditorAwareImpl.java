package com.ecommerce.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class AuditorAwareImpl {
    @Bean
    public AuditorAware<String> auditorProvider() {
        // In real apps, fetch from SecurityContext
        return () -> Optional.of("system");
    }
} 