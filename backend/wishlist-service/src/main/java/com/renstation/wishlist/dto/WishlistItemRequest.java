package com.renstation.wishlist.dto;
import lombok.Data;
import java.util.UUID;

@Data
public class WishlistItemRequest {
    private UUID gameId;
    private UUID listingId;
}
