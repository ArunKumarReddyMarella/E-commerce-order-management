package com.ecommerce.userservice.mapper;

import com.ecommerce.userservice.dto.AddressDTO;
import com.ecommerce.userservice.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    private AddressMapper() {}
    
    public static AddressDTO toDTO(Address address) {
        if (address == null) {
            return null;
        }
        
        AddressDTO dto = new AddressDTO();
        if (address.getId() != null) {
            dto.setId(address.getId());
        }
        if (address.getUserId() != null) {
            dto.setUserId(address.getUserId());
        }
        if (address.getAddress() != null) {
            dto.setAddress(address.getAddress());
        }
        if (address.getCity() != null) {
            dto.setCity(address.getCity());
        }
        if (address.getState() != null) {
            dto.setState(address.getState());
        }
        if (address.getStateCode() != null) {
            dto.setStateCode(address.getStateCode());
        }
        if (address.getPostalCode() != null) {
            dto.setPostalCode(address.getPostalCode());
        }
        if (address.getCountry() != null) {
            dto.setCountry(address.getCountry());
        }
        if (address.getLat() != null) {
            dto.setLat(address.getLat());
        }
        if (address.getLng() != null) {
            dto.setLng(address.getLng());
        }
        return dto;
    }
    
    public static Address toEntity(AddressDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Address address = new Address();
        if (dto.getId() != null) {
            address.setId(dto.getId());
        }
        if (dto.getUserId() != null) {
            address.setUserId(dto.getUserId());
        }
        if (dto.getAddress() != null) {
            address.setAddress(dto.getAddress());
        }
        if (dto.getCity() != null) {
            address.setCity(dto.getCity());
        }
        if (dto.getState() != null) {
            address.setState(dto.getState());
        }
        if (dto.getStateCode() != null) {
            address.setStateCode(dto.getStateCode());
        }
        if (dto.getPostalCode() != null) {
            address.setPostalCode(dto.getPostalCode());
        }
        if (dto.getCountry() != null) {
            address.setCountry(dto.getCountry());
        }
        if (dto.getLat() != null) {
            address.setLat(dto.getLat());
        }
        if (dto.getLng() != null) {
            address.setLng(dto.getLng());
        }
        return address;
    }
} 