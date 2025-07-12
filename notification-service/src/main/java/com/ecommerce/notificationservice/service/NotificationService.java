package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.dto.NotificationDTO;
import com.ecommerce.notificationservice.dto.PageRequestDTO;
import com.ecommerce.notificationservice.mapper.NotificationMapper;
import com.ecommerce.notificationservice.model.Notification;
import com.ecommerce.notificationservice.repository.NotificationRepository;
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
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        Notification notification = NotificationMapper.toEntity(notificationDTO);
        Notification saved = notificationRepository.save(notification);
        return NotificationMapper.toDTO(saved);
    }

    public Optional<NotificationDTO> getNotificationById(Long id) {
        return notificationRepository.findById(id).map(NotificationMapper::toDTO);
    }

    public NotificationDTO updateNotification(NotificationDTO notificationDTO) {
        Notification notification = NotificationMapper.toEntity(notificationDTO);
        Notification updated = notificationRepository.save(notification);
        return NotificationMapper.toDTO(updated);
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    public List<NotificationDTO> listNotifications() {
        return notificationRepository.findAll().stream().map(NotificationMapper::toDTO).collect(Collectors.toList());
    }

    public Page<NotificationDTO> listNotificationsPaginated(PageRequestDTO pageRequestDTO) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDTO.getSortDirection()), pageRequestDTO.getSortBy());
        Pageable pageable = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sort);
        return notificationRepository.findAll(pageable).map(NotificationMapper::toDTO);
    }

    // Example: list notifications by userId (to be implemented in repository if needed)
    public List<NotificationDTO> listByUserId(Long userId) {
        // return notificationRepository.findByUserId(userId).stream().map(NotificationMapper::toDTO).collect(Collectors.toList());
        return List.of();
    }
} 