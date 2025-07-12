package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.AddressDTO;
import com.ecommerce.orderservice.dto.PageRequestDTO;
import com.ecommerce.orderservice.mapper.AddressMapper;
import com.ecommerce.orderservice.model.Address;
import com.ecommerce.orderservice.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Page<AddressDTO> listAddresses(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortBy());
        PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        return addressRepository.findAll(pageRequest).map(AddressMapper::toDTO);
    }
} 