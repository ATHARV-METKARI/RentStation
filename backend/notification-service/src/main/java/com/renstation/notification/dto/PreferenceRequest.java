package com.renstation.notification.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PreferenceRequest {
    private boolean emailEnabled;
    private boolean smsEnabled;
    private boolean inAppEnabled;
    private LocalDateTime mutedUntil;
}
