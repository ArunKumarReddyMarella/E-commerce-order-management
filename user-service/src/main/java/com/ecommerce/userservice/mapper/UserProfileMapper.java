package com.ecommerce.userservice.mapper;

import com.ecommerce.userservice.dto.UserProfileDTO;
import com.ecommerce.userservice.model.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {
    private UserProfileMapper() {}
    public static UserProfileDTO toDTO(UserProfile userProfile) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(userProfile.getId());
        dto.setUserId(userProfile.getUserId());
        dto.setAddress(userProfile.getAddress());
        dto.setDateOfBirth(userProfile.getDateOfBirth());
        dto.setGender(userProfile.getGender());
        dto.setAvatarUrl(userProfile.getAvatarUrl());
        return dto;
    }

    public static UserProfile toEntity(UserProfileDTO dto) {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(dto.getId());
        userProfile.setUserId(dto.getUserId());
        userProfile.setAddress(dto.getAddress());
        userProfile.setDateOfBirth(dto.getDateOfBirth());
        userProfile.setGender(dto.getGender());
        userProfile.setAvatarUrl(dto.getAvatarUrl());
        return userProfile;
    }
} 