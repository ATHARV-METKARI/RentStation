package com.renstation.notification.service;

import com.renstation.common.exception.ResourceNotFoundException;
import com.renstation.notification.dto.EventPayload;
import com.renstation.notification.entity.Notification;
import com.renstation.notification.entity.NotificationPreference;
import com.renstation.notification.repository.NotificationPreferenceRepository;
import com.renstation.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationPreferenceRepository preferenceRepository;

    @Transactional
    public void processEvent(EventPayload event) {
        log.info("Processing event: {} for user: {}", event.getEventType(), event.getTargetUserId());

        NotificationPreference prefs = preferenceRepository.findByUserId(event.getTargetUserId())
                .orElseGet(() -> createDefaultPreference(event.getTargetUserId()));

        if (prefs.getMutedUntil() != null && prefs.getMutedUntil().isAfter(LocalDateTime.now())) {
            log.info("User {} is muted until {}. Skipping notification.", event.getTargetUserId(), prefs.getMutedUntil());
            return;
        }

        if (prefs.isInAppEnabled()) {
            Notification notification = Notification.builder()
                    .userId(event.getTargetUserId())
                    .title("New Update: " + event.getEventType())
                    .message("Details: " + (event.getData() != null ? event.getData().toString() : "None"))
                    .type(event.getEventType())
                    .status("DELIVERED")
                    .isRead(false)
                    .build();
            notificationRepository.save(notification);
        }
        
        // Future: Check prefs.isSmsEnabled() and call Twilio API here
        // Future: Check prefs.isEmailEnabled() and call SendGrid API here
    }

    public Page<Notification> getMyNotifications(UUID userId, Pageable pageable) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    @Transactional
    public void markAsRead(UUID userId, UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        
        if (!notification.getUserId().equals(userId)) {
            throw new SecurityException("Cannot read someone else's notification");
        }
        
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Transactional
    public void markAllAsRead(UUID userId) {
        List<Notification> unread = notificationRepository.findByUserIdAndIsReadFalse(userId);
        unread.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(unread);
    }

    private NotificationPreference createDefaultPreference(UUID userId) {
        NotificationPreference pref = NotificationPreference.builder()
                .userId(userId)
                .emailEnabled(true)
                .smsEnabled(true)
                .inAppEnabled(true)
                .build();
        return preferenceRepository.save(pref);
    }
}
