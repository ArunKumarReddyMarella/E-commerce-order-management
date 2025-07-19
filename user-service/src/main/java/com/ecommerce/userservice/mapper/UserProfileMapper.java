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
        dto.setFirstName(userProfile.getFirstName());
        dto.setLastName(userProfile.getLastName());
        dto.setMaidenName(userProfile.getMaidenName());
        dto.setAge(userProfile.getAge());
        dto.setGender(userProfile.getGender());
        dto.setPhone(userProfile.getPhone());
        dto.setBirthDate(userProfile.getBirthDate());
        dto.setImage(userProfile.getImage());
        dto.setBloodGroup(userProfile.getBloodGroup());
        dto.setHeight(userProfile.getHeight());
        dto.setWeight(userProfile.getWeight());
        dto.setEyeColor(userProfile.getEyeColor());
        dto.setHairColor(userProfile.getHairColor());
        dto.setHairType(userProfile.getHairType());
        dto.setIp(userProfile.getIp());
        dto.setMacAddress(userProfile.getMacAddress());
        return dto;
    }

    public static UserProfile toEntity(UserProfileDTO dto) {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(dto.getId());
        userProfile.setUserId(dto.getUserId());
        userProfile.setFirstName(dto.getFirstName());
        userProfile.setLastName(dto.getLastName());
        userProfile.setMaidenName(dto.getMaidenName());
        userProfile.setAge(dto.getAge());
        userProfile.setGender(dto.getGender());
        userProfile.setPhone(dto.getPhone());
        userProfile.setBirthDate(dto.getBirthDate());
        userProfile.setImage(dto.getImage());
        userProfile.setBloodGroup(dto.getBloodGroup());
        userProfile.setHeight(dto.getHeight());
        userProfile.setWeight(dto.getWeight());
        userProfile.setEyeColor(dto.getEyeColor());
        userProfile.setHairColor(dto.getHairColor());
        userProfile.setHairType(dto.getHairType());
        userProfile.setIp(dto.getIp());
        userProfile.setMacAddress(dto.getMacAddress());
        return userProfile;
    }
} 