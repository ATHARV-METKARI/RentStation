package com.renstation.payment.controller;

import com.renstation.common.dto.StandardApiResponse;
import com.renstation.payment.dto.PaymentRequest;
import com.renstation.payment.dto.WebhookRequest;
import com.renstation.payment.entity.Payment;
import com.renstation.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initiate")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<StandardApiResponse<Payment>> initiatePayment(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            Authentication auth, 
            @Valid @RequestBody PaymentRequest request) {
        Payment payment = paymentService.initiatePayment(UUID.fromString(auth.getName()), idempotencyKey, request);
        return ResponseEntity.ok(StandardApiResponse.<Payment>builder().success(true).data(payment).build());
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody WebhookRequest request) {
        // In a real system, you must validate HMAC signatures here!
        log.info("Received webhook event: {}", request.getEventType());
        paymentService.handleWebhook(request);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<StandardApiResponse<List<Payment>>> getMyPayments(Authentication auth) {
        List<Payment> payments = paymentService.getMyPayments(UUID.fromString(auth.getName()));
        return ResponseEntity.ok(StandardApiResponse.<List<Payment>>builder().success(true).data(payments).build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('ADMIN')")
    public ResponseEntity<StandardApiResponse<Payment>> getPaymentById(@PathVariable UUID id) {
        Payment payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(StandardApiResponse.<Payment>builder().success(true).data(payment).build());
    }
}
