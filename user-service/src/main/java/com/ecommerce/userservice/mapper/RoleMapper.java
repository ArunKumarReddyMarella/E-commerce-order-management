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
        if (role == null) {
            return null;
        }
        
        RoleDTO dto = new RoleDTO();
        if (role.getId() != null) {
            dto.setId(role.getId());
        }
        if (role.getName() != null) {
            dto.setName(role.getName());
        }
        if (role.getDescription() != null) {
            dto.setDescription(role.getDescription());
        }
        return dto;
    }

    public static Role toEntity(RoleDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Role role = new Role();
        if (dto.getId() != null) {
            role.setId(dto.getId());
        }
        if (dto.getName() != null) {
            role.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            role.setDescription(dto.getDescription());
        }
        return role;
    }
} 