package com.ecommerce.userservice.mapper;

import com.ecommerce.userservice.dto.AddressDTO;
import com.ecommerce.userservice.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    private AddressMapper() {}
    public static AddressDTO toDTO(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setId(address.getId());
        dto.setUserId(address.getUserId());
        dto.setAddress(address.getAddress());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setStateCode(address.getStateCode());
        dto.setPostalCode(address.getPostalCode());
        dto.setCountry(address.getCountry());
        dto.setLat(address.getLat());
        dto.setLng(address.getLng());
        return dto;
    }
    public static Address toEntity(AddressDTO dto) {
        Address address = new Address();
        address.setId(dto.getId());
        address.setUserId(dto.getUserId());
        address.setAddress(dto.getAddress());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setStateCode(dto.getStateCode());
        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        address.setLat(dto.getLat());
        address.setLng(dto.getLng());
        return address;
    }
} 