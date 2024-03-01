package com.renstation.notification.controller;

import com.renstation.common.dto.PageResponse;
import com.renstation.common.dto.StandardApiResponse;
import com.renstation.notification.entity.Notification;
import com.renstation.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<StandardApiResponse<PageResponse<Notification>>> getMyNotifications(
            Authentication auth, Pageable pageable) {
        Page<Notification> page = notificationService.getMyNotifications(UUID.fromString(auth.getName()), pageable);
        return ResponseEntity.ok(StandardApiResponse.<PageResponse<Notification>>builder()
                .success(true).data(PageResponse.of(page)).build());
    }

    @PatchMapping("/{id}/read")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<StandardApiResponse<Void>> markAsRead(
            @PathVariable UUID id, Authentication auth) {
        notificationService.markAsRead(UUID.fromString(auth.getName()), id);
        return ResponseEntity.ok(StandardApiResponse.<Void>builder().success(true).build());
    }

    @PatchMapping("/read-all")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<StandardApiResponse<Void>> markAllAsRead(Authentication auth) {
        notificationService.markAllAsRead(UUID.fromString(auth.getName()));
        return ResponseEntity.ok(StandardApiResponse.<Void>builder().success(true).build());
    }
}
