package com.renstation.payment.service;

import com.renstation.common.exception.BusinessException;
import com.renstation.common.exception.ResourceNotFoundException;
import com.renstation.payment.client.RentalClient;
import com.renstation.payment.dto.PaymentRequest;
import com.renstation.payment.dto.WebhookRequest;
import com.renstation.payment.entity.Payment;
import com.renstation.payment.entity.PaymentTransaction;
import com.renstation.payment.repository.PaymentRepository;
import com.renstation.payment.repository.PaymentTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentTransactionRepository transactionRepository;
    private final RentalClient rentalClient;

    @Transactional
    public Payment initiatePayment(UUID userId, String idempotencyKey, PaymentRequest request) {
        
        Optional<Payment> existingPayment = paymentRepository.findByIdempotencyKey(idempotencyKey);
        if (existingPayment.isPresent()) {
            log.info("Idempotent request detected. Returning existing payment status.");
            return existingPayment.get();
        }

        Payment payment = Payment.builder()
                .rentalId(request.getRentalId())
                .userId(userId)
                .amount(request.getAmount())
                .platformFee(request.getPlatformFee())
                .sellerEarning(request.getSellerEarning())
                .currency(request.getCurrency())
                .status("PENDING")
                .idempotencyKey(idempotencyKey)
                .build();
        
        payment = paymentRepository.save(payment);
        
        PaymentTransaction tx = PaymentTransaction.builder()
                .payment(payment)
                .transactionType("AUTHORIZATION")
                .amount(payment.getAmount())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
        transactionRepository.save(tx);

        return payment;
    }

    @Transactional
    public void handleWebhook(WebhookRequest webhookRequest) {
        // Find by idempotencyKey to ensure we link the async gateway payload correctly
        Payment payment = paymentRepository.findByIdempotencyKey(webhookRequest.getIdempotencyKey())
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for key: " + webhookRequest.getIdempotencyKey()));

        if ("SUCCESS".equals(payment.getStatus())) {
            log.info("Webhook duplicate. Payment already successful.");
            return;
        }

        if ("payment.success".equals(webhookRequest.getEventType())) {
            payment.setStatus("SUCCESS");
            payment.setTransactionId(webhookRequest.getTransactionId());
            paymentRepository.save(payment);

            PaymentTransaction tx = PaymentTransaction.builder()
                    .payment(payment)
                    .transactionType("CAPTURE")
                    .amount(webhookRequest.getAmount())
                    .status("SUCCESS")
                    .gatewayReference(webhookRequest.getTransactionId())
                    .createdAt(LocalDateTime.now())
                    .build();
            transactionRepository.save(tx);

            // S2S Orchestration: Tell Rental Service to Confirm!
            log.info("Notifying Rental Service for rentalId: {}", payment.getRentalId());
            try {
                rentalClient.updateRentalStatus(payment.getRentalId(), "CONFIRMED", "Payment successful.");
            } catch (Exception e) {
                log.error("Failed to notify rental service. Saga compensation needed for {}", payment.getId());
                // Throwing here will rollback the local DB transaction
                throw new BusinessException("S2S Notification Failed");
            }
        }
    }

    public Payment getPaymentById(UUID id) {
        return paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
    }

    public List<Payment> getMyPayments(UUID userId) {
        return paymentRepository.findByUserId(userId);
    }
}
