package com.ecommerce.orderservice.mapper;

import com.ecommerce.orderservice.dto.AddressDTO;
import com.ecommerce.orderservice.model.Address;

public class AddressMapper {
    public static AddressDTO toDTO(Address address) {
        if (address == null) {
            return null;
        }
        return AddressDTO.builder()
                .id(address.getId())
                .userId(address.getUserId())
                .address(address.getAddress())
                .city(address.getCity())
                .state(address.getState())
                .stateCode(address.getStateCode())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .lat(address.getLat())
                .lng(address.getLng())
                .build();
    }

    /**
     * Converts an AddressDTO to an Address entity.
     *
     * @param dto the AddressDTO to convert, can be null
     * @return the converted Address, or null if the input is null
     */
    public static Address toEntity(AddressDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Address.AddressBuilder builder = Address.builder();
        
        if (dto.getId() != null) {
            builder.id(dto.getId());
        }
        if (dto.getUserId() != null) {
            builder.userId(dto.getUserId());
        }
        if (dto.getAddress() != null) {
            builder.address(dto.getAddress());
        }
        if (dto.getCity() != null) {
            builder.city(dto.getCity());
        }
        if (dto.getState() != null) {
            builder.state(dto.getState());
        }
        if (dto.getStateCode() != null) {
            builder.stateCode(dto.getStateCode());
        }
        if (dto.getPostalCode() != null) {
            builder.postalCode(dto.getPostalCode());
        }
        if (dto.getCountry() != null) {
            builder.country(dto.getCountry());
        }
        if (dto.getLat() != null) {
            builder.lat(dto.getLat());
        }
        if (dto.getLng() != null) {
            builder.lng(dto.getLng());
        }
        
        return builder.build();
    }
}