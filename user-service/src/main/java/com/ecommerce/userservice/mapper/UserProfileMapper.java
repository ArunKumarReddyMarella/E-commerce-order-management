package com.ecommerce.userservice.mapper;

import com.ecommerce.userservice.dto.UserProfileDTO;
import com.ecommerce.userservice.model.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {
    private UserProfileMapper() {}
    public static UserProfileDTO toDTO(UserProfile userProfile) {
        if (userProfile == null) {
            return null;
        }
        
        UserProfileDTO dto = new UserProfileDTO();
        if (userProfile.getId() != null) {
            dto.setId(userProfile.getId());
        }
        if (userProfile.getUserId() != null) {
            dto.setUserId(userProfile.getUserId());
        }
        if (userProfile.getFirstName() != null) {
            dto.setFirstName(userProfile.getFirstName());
        }
        if (userProfile.getLastName() != null) {
            dto.setLastName(userProfile.getLastName());
        }
        if (userProfile.getMaidenName() != null) {
            dto.setMaidenName(userProfile.getMaidenName());
        }
        if (userProfile.getAge() != null) {
            dto.setAge(userProfile.getAge());
        }
        if (userProfile.getGender() != null) {
            dto.setGender(userProfile.getGender());
        }
        if (userProfile.getPhone() != null) {
            dto.setPhone(userProfile.getPhone());
        }
        if (userProfile.getBirthDate() != null) {
            dto.setBirthDate(userProfile.getBirthDate());
        }
        if (userProfile.getImage() != null) {
            dto.setImage(userProfile.getImage());
        }
        if (userProfile.getBloodGroup() != null) {
            dto.setBloodGroup(userProfile.getBloodGroup());
        }
        if (userProfile.getHeight() != null) {
            dto.setHeight(userProfile.getHeight());
        }
        if (userProfile.getWeight() != null) {
            dto.setWeight(userProfile.getWeight());
        }
        if (userProfile.getEyeColor() != null) {
            dto.setEyeColor(userProfile.getEyeColor());
        }
        if (userProfile.getHairColor() != null) {
            dto.setHairColor(userProfile.getHairColor());
        }
        if (userProfile.getHairType() != null) {
            dto.setHairType(userProfile.getHairType());
        }
        if (userProfile.getIp() != null) {
            dto.setIp(userProfile.getIp());
        }
        if (userProfile.getMacAddress() != null) {
            dto.setMacAddress(userProfile.getMacAddress());
        }
        return dto;
    }

    public static UserProfile toEntity(UserProfileDTO dto) {
        if (dto == null) {
            return null;
        }
        
        UserProfile userProfile = new UserProfile();
        if (dto.getId() != null) {
            userProfile.setId(dto.getId());
        }
        if (dto.getUserId() != null) {
            userProfile.setUserId(dto.getUserId());
        }
        if (dto.getFirstName() != null) {
            userProfile.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            userProfile.setLastName(dto.getLastName());
        }
        if (dto.getMaidenName() != null) {
            userProfile.setMaidenName(dto.getMaidenName());
        }
        if (dto.getAge() != null) {
            userProfile.setAge(dto.getAge());
        }
        if (dto.getGender() != null) {
            userProfile.setGender(dto.getGender());
        }
        if (dto.getPhone() != null) {
            userProfile.setPhone(dto.getPhone());
        }
        if (dto.getBirthDate() != null) {
            userProfile.setBirthDate(dto.getBirthDate());
        }
        if (dto.getImage() != null) {
            userProfile.setImage(dto.getImage());
        }
        if (dto.getBloodGroup() != null) {
            userProfile.setBloodGroup(dto.getBloodGroup());
        }
        if (dto.getHeight() != null) {
            userProfile.setHeight(dto.getHeight());
        }
        if (dto.getWeight() != null) {
            userProfile.setWeight(dto.getWeight());
        }
        if (dto.getEyeColor() != null) {
            userProfile.setEyeColor(dto.getEyeColor());
        }
        if (dto.getHairColor() != null) {
            userProfile.setHairColor(dto.getHairColor());
        }
        if (dto.getHairType() != null) {
            userProfile.setHairType(dto.getHairType());
        }
        if (dto.getIp() != null) {
            userProfile.setIp(dto.getIp());
        }
        if (dto.getMacAddress() != null) {
            userProfile.setMacAddress(dto.getMacAddress());
        }
        return userProfile;
    }
}