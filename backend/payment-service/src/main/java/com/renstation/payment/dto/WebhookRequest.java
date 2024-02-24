package com.renstation.payment.dto;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class WebhookRequest {
    private String eventType; // e.g. payment.success
    private String transactionId;
    private String idempotencyKey;
    private BigDecimal amount;
    private String currency;
    private String status;
}
