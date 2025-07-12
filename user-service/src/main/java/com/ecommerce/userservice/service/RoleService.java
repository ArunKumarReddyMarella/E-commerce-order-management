package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.RoleDTO;
import com.ecommerce.userservice.dto.PageRequestDTO;
import com.ecommerce.userservice.mapper.RoleMapper;
import com.ecommerce.userservice.model.Role;
import com.ecommerce.userservice.repository.RoleRepository;
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
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = RoleMapper.toEntity(roleDTO);
        Role saved = roleRepository.save(role);
        return RoleMapper.toDTO(saved);
    }

    public Optional<RoleDTO> getRoleById(Long id) {
        return roleRepository.findById(id).map(RoleMapper::toDTO);
    }

    public RoleDTO updateRole(RoleDTO roleDTO) {
        Role role = RoleMapper.toEntity(roleDTO);
        Role updated = roleRepository.save(role);
        return RoleMapper.toDTO(updated);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public List<RoleDTO> listRoles() {
        return roleRepository.findAll().stream().map(RoleMapper::toDTO).collect(Collectors.toList());
    }

    public Page<RoleDTO> listRolesPaginated(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortBy());
        Pageable pageable = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        return roleRepository.findAll(pageable).map(RoleMapper::toDTO);
    }
} 