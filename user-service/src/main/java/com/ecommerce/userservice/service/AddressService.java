package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.AddressDTO;
import com.ecommerce.userservice.mapper.AddressMapper;
import com.ecommerce.userservice.model.Address;
import com.ecommerce.userservice.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address address = AddressMapper.toEntity(addressDTO);
        Address saved = addressRepository.save(address);
        return AddressMapper.toDTO(saved);
    }

    public Optional<AddressDTO> getAddressById(Long id) {
        return addressRepository.findById(id).map(AddressMapper::toDTO);
    }

    public AddressDTO updateAddress(AddressDTO addressDTO) {
        Address address = AddressMapper.toEntity(addressDTO);
        Address updated = addressRepository.save(address);
        return AddressMapper.toDTO(updated);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    public List<AddressDTO> listAddresses() {
        return addressRepository.findAll().stream().map(AddressMapper::toDTO).collect(Collectors.toList());
    }
} 