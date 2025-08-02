package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.AddressDTO;
import com.ecommerce.userservice.dto.UserDTO;
import com.ecommerce.userservice.dto.PageRequestDTO;
import com.ecommerce.userservice.mapper.AddressMapper;
import com.ecommerce.userservice.mapper.UserMapper;
import com.ecommerce.userservice.model.User;
import com.ecommerce.userservice.repository.AddressRepository;
import com.ecommerce.userservice.repository.RoleRepository;
import com.ecommerce.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AddressRepository addressRepository;


    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        user.setRole(roleRepository.findById(userDTO.getRole().getId()).orElse(null));
        User saved = userRepository.save(user);
        return UserMapper.toDTO(saved);
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(UserMapper::toDTO);
    }

    @Transactional
    public UserDTO updateUser(UserDTO userDTO) {
        // Fetch the existing user
        User existingUser = userRepository.findById(userDTO.getId())
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userDTO.getId()));
        
        // Merge the DTO data with the existing user
        User updatedUser = UserMapper.mergeWithDTO(existingUser, userDTO);
        
        // Handle role update if role is provided
        if (userDTO.getRole() != null && userDTO.getRole().getId() != null) {
            updatedUser.setRole(roleRepository.findById(userDTO.getRole().getId())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + userDTO.getRole().getId())));
        }
        
        // Save and return the updated user
        User savedUser = userRepository.save(updatedUser);
        return UserMapper.toDTO(savedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserDTO> listUsers() {
        return userRepository.findAll().stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    public Page<UserDTO> listUsersPaginated(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortBy());
        Pageable pageable = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        return userRepository.findAll(pageable).map(UserMapper::toDTO);
    }

    // Example: find user by email (to be implemented in repository if needed)
    public Optional<UserDTO> findByEmail(String email) {
         return userRepository.findByEmail(email).map(UserMapper::toDTO);
    }

    public Optional<AddressDTO> getUserAddress(Long userId) {
        return addressRepository.findByUserId(userId).map(AddressMapper::toDTO);
    }
}