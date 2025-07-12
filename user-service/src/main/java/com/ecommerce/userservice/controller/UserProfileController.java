package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.UserProfileDTO;
import com.ecommerce.userservice.dto.PageRequestDTO;
import com.ecommerce.userservice.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user-profiles")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<UserProfileDTO> create(@RequestBody UserProfileDTO userProfileDTO) {
        return ResponseEntity.ok(userProfileService.createUserProfile(userProfileDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> getById(@PathVariable Long id) {
        return userProfileService.getUserProfileById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDTO> update(@PathVariable Long id, @RequestBody UserProfileDTO userProfileDTO) {
        userProfileDTO.setId(id);
        return ResponseEntity.ok(userProfileService.updateUserProfile(userProfileDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userProfileService.deleteUserProfile(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserProfileDTO>> list() {
        return ResponseEntity.ok(userProfileService.listUserProfiles());
    }

    @PostMapping("/list")
    public ResponseEntity<Page<UserProfileDTO>> listPaginated(@RequestBody PageRequestDTO pageRequestDTO) {
        return ResponseEntity.ok(userProfileService.listUserProfilesPaginated(pageRequestDTO));
    }
} 