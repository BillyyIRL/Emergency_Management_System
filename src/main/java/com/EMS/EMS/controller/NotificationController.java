package com.EMS.EMS.controller;

import com.EMS.EMS.entity.Notification;
import com.EMS.EMS.repository.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Get all notifications for a hospital
    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<List<Notification>> getHospitalNotifications(
            @PathVariable Long hospitalId) {
        return ResponseEntity.ok(
                notificationRepository.findByHospitalId(hospitalId)
        );
    }

    // Get only unread notifications
    @GetMapping("/hospital/{hospitalId}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(
            @PathVariable Long hospitalId) {
        return ResponseEntity.ok(
                notificationRepository.findByHospitalIdAndIsRead(hospitalId, false)
        );
    }
}