package com.renstation.rental.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RentalRequest {
    @NotNull
    private UUID listingId;
    @NotNull
    private UUID gameId;
    @NotNull
    private UUID ownerId;
    @NotNull
    private String rentalType;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
    @NotNull
    private BigDecimal price;
    @NotNull
    private BigDecimal deposit;
}
