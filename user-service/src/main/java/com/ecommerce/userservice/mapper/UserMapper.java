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
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setStatus(user.getStatus());
        if (user.getRole() != null) {
            dto.setRole(RoleMapper.toDTO(user.getRole()));
        }
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setStatus(dto.getStatus());
        if (dto.getRole() != null) {
            user.setRole(RoleMapper.toEntity(dto.getRole()));
        }
        user.setCreatedAt(dto.getCreatedAt());
        user.setUpdatedAt(dto.getUpdatedAt());
        return user;
    }
} 