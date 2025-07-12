package com.ecommerce.notificationservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageRequestDTO {
    @Min(value = 0, message = "Page number must be 0 or greater")
    private int pageNumber = 0;

    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size must not exceed 100")
    private int pageSize = 20;

    @Pattern(regexp = "^[a-zA-Z0-9_]+$",
            message = "Sort field can only contain alphanumeric characters and underscores")
    private String sortBy = "updatedAt";

    @Pattern(regexp = "(?i)^(ASC|DESC)$",
            message = "Sort direction must be either 'ASC' or 'DESC'")
    private String sortDirection = "DESC";
} 