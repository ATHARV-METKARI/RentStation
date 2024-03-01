package com.renstation.notification.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;
import java.util.Map;

@Data
public class EventPayload {
    @NotNull
    private String eventType; // e.g. RENTAL_CREATED
    @NotNull
    private UUID targetUserId;
    private Map<String, Object> data;
}
