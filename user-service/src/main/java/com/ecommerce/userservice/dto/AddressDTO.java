package com.ecommerce.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private Long id;
    private Long userId;
    private String address;
    private String city;
    private String state;
    private String stateCode;
    private String postalCode;
    private String country;
    private Double lat;
    private Double lng;
} 