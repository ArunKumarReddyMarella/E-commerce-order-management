package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.AddressDTO;
import com.ecommerce.userservice.dto.UserDTO;
import com.ecommerce.userservice.dto.PageRequestDTO;
import com.ecommerce.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //get user address
    @GetMapping("/{id}/address")
    public ResponseEntity<AddressDTO> getUserAddress(@PathVariable Long id) {
        return userService.getUserAddress(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        return ResponseEntity.ok(userService.updateUser(userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> list() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @PostMapping("/list")
    public ResponseEntity<Page<UserDTO>> listPaginated(@RequestBody PageRequestDTO pageRequestDTO) {
        return ResponseEntity.ok(userService.listUsersPaginated(pageRequestDTO));
    }
    
    @GetMapping("/verify/{userId}")
    public ResponseEntity<Boolean> verifyUser(@PathVariable Long userId) {
        Optional<UserDTO> user = userService.getUserById(userId);
        return ResponseEntity.ok(user.isPresent() && user.get().isActive());
    }
} 