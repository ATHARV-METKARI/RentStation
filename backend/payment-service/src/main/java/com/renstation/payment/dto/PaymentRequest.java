package com.renstation.payment.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentRequest {
    @NotNull
    private UUID rentalId;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private BigDecimal platformFee;
    @NotNull
    private BigDecimal sellerEarning;
    @NotNull
    private String currency;
}
