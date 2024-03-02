package com.renstation.review.dto;
import lombok.Data;
import java.util.UUID;

@Data
public class RentalDto {
    private UUID id;
    private UUID listingId;
    private UUID ownerId;
    private UUID renterId;
    private String status;
}
