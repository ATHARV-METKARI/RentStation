package com.renstation.notification.service;

import com.renstation.notification.dto.PreferenceRequest;
import com.renstation.notification.entity.NotificationPreference;
import com.renstation.notification.repository.NotificationPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PreferenceService {

    private final NotificationPreferenceRepository preferenceRepository;

    public NotificationPreference getPreferences(UUID userId) {
        return preferenceRepository.findByUserId(userId)
                .orElseGet(() -> createDefaultPreference(userId));
    }

    @Transactional
    public NotificationPreference updatePreferences(UUID userId, PreferenceRequest request) {
        NotificationPreference prefs = getPreferences(userId);
        prefs.setEmailEnabled(request.isEmailEnabled());
        prefs.setSmsEnabled(request.isSmsEnabled());
        prefs.setInAppEnabled(request.isInAppEnabled());
        prefs.setMutedUntil(request.getMutedUntil());
        return preferenceRepository.save(prefs);
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
