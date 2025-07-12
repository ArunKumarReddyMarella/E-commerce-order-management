package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.UserDTO;
import com.ecommerce.userservice.dto.PageRequestDTO;
import com.ecommerce.userservice.mapper.UserMapper;
import com.ecommerce.userservice.model.User;
import com.ecommerce.userservice.repository.RoleRepository;
import com.ecommerce.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        user.setRole(roleRepository.findById(userDTO.getRole().getId()).orElse(null));
        User saved = userRepository.save(user);
        return UserMapper.toDTO(saved);
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(UserMapper::toDTO);
    }

    public UserDTO updateUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        user.setRole(roleRepository.findById(userDTO.getRole().getId()).orElse(null));
        User updated = userRepository.save(user);
        return UserMapper.toDTO(updated);
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
} 