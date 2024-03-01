package com.renstation.notification.controller;

import com.renstation.common.dto.StandardApiResponse;
import com.renstation.notification.dto.PreferenceRequest;
import com.renstation.notification.entity.NotificationPreference;
import com.renstation.notification.service.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications/preferences")
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;

    @GetMapping
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<StandardApiResponse<NotificationPreference>> getMyPreferences(Authentication auth) {
        NotificationPreference prefs = preferenceService.getPreferences(UUID.fromString(auth.getName()));
        return ResponseEntity.ok(StandardApiResponse.<NotificationPreference>builder().success(true).data(prefs).build());
    }

    @PutMapping
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<StandardApiResponse<NotificationPreference>> updateMyPreferences(
            Authentication auth, @RequestBody PreferenceRequest request) {
        NotificationPreference prefs = preferenceService.updatePreferences(UUID.fromString(auth.getName()), request);
        return ResponseEntity.ok(StandardApiResponse.<NotificationPreference>builder().success(true).data(prefs).build());
    }
}
