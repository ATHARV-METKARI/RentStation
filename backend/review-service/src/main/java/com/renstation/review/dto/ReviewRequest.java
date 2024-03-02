package com.renstation.review.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class ReviewRequest {
    @NotNull
    private UUID rentalId;
    private UUID reviewedUserId;
    private UUID listingId;
    
    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;
    
    private String comment;
    
    @NotNull
    private String reviewType; // RENTER_TO_SELLER, SELLER_TO_RENTER
}
