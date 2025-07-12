package com.ecommerce.userservice.mapper;

import com.ecommerce.userservice.dto.RoleDTO;
import com.ecommerce.userservice.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    private RoleMapper() {
        // Private constructor to prevent instantiation
    }

    public static RoleDTO toDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        return dto;
    }

    public static Role toEntity(RoleDTO dto) {
        Role role = new Role();
        role.setId(dto.getId());
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        return role;
    }
} 