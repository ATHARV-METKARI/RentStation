package com.renstation.notification.controller;

import com.renstation.common.dto.StandardApiResponse;
import com.renstation.notification.dto.EventPayload;
import com.renstation.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<StandardApiResponse<Void>> triggerEvent(@Valid @RequestBody EventPayload payload) {
        notificationService.processEvent(payload);
        return ResponseEntity.ok(StandardApiResponse.<Void>builder().success(true).build());
    }
}
