package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.UserProfileDTO;
import com.ecommerce.userservice.dto.PageRequestDTO;
import com.ecommerce.userservice.mapper.UserProfileMapper;
import com.ecommerce.userservice.model.UserProfile;
import com.ecommerce.userservice.repository.UserProfileRepository;
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
public class UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    public UserProfileDTO createUserProfile(UserProfileDTO userProfileDTO) {
        UserProfile userProfile = UserProfileMapper.toEntity(userProfileDTO);
        UserProfile saved = userProfileRepository.save(userProfile);
        return UserProfileMapper.toDTO(saved);
    }

    public Optional<UserProfileDTO> getUserProfileById(Long id) {
        return userProfileRepository.findById(id).map(UserProfileMapper::toDTO);
    }

    public UserProfileDTO updateUserProfile(UserProfileDTO userProfileDTO) {
        UserProfile userProfile = UserProfileMapper.toEntity(userProfileDTO);
        UserProfile updated = userProfileRepository.save(userProfile);
        return UserProfileMapper.toDTO(updated);
    }

    public void deleteUserProfile(Long id) {
        userProfileRepository.deleteById(id);
    }

    public List<UserProfileDTO> listUserProfiles() {
        return userProfileRepository.findAll().stream().map(UserProfileMapper::toDTO).collect(Collectors.toList());
    }

    public Page<UserProfileDTO> listUserProfilesPaginated(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortBy());
        Pageable pageable = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        return userProfileRepository.findAll(pageable).map(UserProfileMapper::toDTO);
    }
} 