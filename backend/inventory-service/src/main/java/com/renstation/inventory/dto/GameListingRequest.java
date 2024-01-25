package com.renstation.inventory.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class GameListingRequest {
    @NotNull
    private UUID gameId;
    @NotNull
    private UUID accountId;
    @NotNull
    private String listingType;
    @NotNull
    private BigDecimal price;
    @NotNull
    private BigDecimal securityDeposit;
    private String conditionNotes;
}
