package com.ecommerce.userservice.mapper;

import com.ecommerce.userservice.dto.UserDTO;
import com.ecommerce.userservice.dto.RoleDTO;
import com.ecommerce.userservice.model.User;
import com.ecommerce.userservice.model.Role;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private UserMapper() {
        // Private constructor to prevent instantiation
    }

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        if (user.getRole() != null) {
            dto.setRole(RoleMapper.toDTO(user.getRole()));
        }
        if (user.getCreatedAt() != null) {
            dto.setCreatedAt(user.getCreatedAt());
        }
        if (user.getUpdatedAt() != null) {
            dto.setUpdatedAt(user.getUpdatedAt());
        }
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        
        User user = new User();
        if (dto.getId() != null) {
            user.setId(dto.getId());
        }
        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }
        if (dto.getRole() != null) {
            user.setRole(RoleMapper.toEntity(dto.getRole()));
        }
        if (dto.getCreatedAt() != null) {
            user.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            user.setUpdatedAt(dto.getUpdatedAt());
        }
        return user;
    }
} 