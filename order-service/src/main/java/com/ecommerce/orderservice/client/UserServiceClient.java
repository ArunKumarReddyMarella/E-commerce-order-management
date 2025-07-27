package com.ecommerce.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import com.ecommerce.orderservice.dto.UserDTO;
import com.ecommerce.orderservice.dto.AddressDTO;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/api/users/{userId}")
    UserDTO getUserById(@PathVariable("userId") Long userId);

    @GetMapping("/api/users/{userId}/addresses")
    List<AddressDTO> getUserAddresses(@PathVariable("userId") Long userId);
    
    @GetMapping("api/users/verify/{userId}")
    boolean verifyUser(@PathVariable("userId") Long userId);
} 