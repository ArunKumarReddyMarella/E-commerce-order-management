package com.ecommerce.notificationservice.controller;

import com.ecommerce.notificationservice.dto.NotificationDTO;
import com.ecommerce.notificationservice.dto.PageRequestDTO;
import com.ecommerce.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationDTO> create(@RequestBody NotificationDTO notificationDTO) {
        return ResponseEntity.ok(notificationService.createNotification(notificationDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getById(@PathVariable Long id) {
        return notificationService.getNotificationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationDTO> update(@PathVariable Long id, @RequestBody NotificationDTO notificationDTO) {
        notificationDTO.setId(id);
        return ResponseEntity.ok(notificationService.updateNotification(notificationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> list() {
        return ResponseEntity.ok(notificationService.listNotifications());
    }

    @PostMapping("/list")
    public ResponseEntity<Page<NotificationDTO>> listPaginated(@RequestBody PageRequestDTO pageRequestDTO) {
        return ResponseEntity.ok(notificationService.listNotificationsPaginated(pageRequestDTO));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> listByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.listByUserId(userId));
    }
} 