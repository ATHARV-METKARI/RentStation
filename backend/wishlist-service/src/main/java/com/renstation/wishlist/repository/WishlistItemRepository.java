package com.renstation.wishlist.repository;
import com.renstation.wishlist.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, UUID> {
    boolean existsByWishlistIdAndGameId(UUID wishlistId, UUID gameId);
    boolean existsByWishlistIdAndListingId(UUID wishlistId, UUID listingId);
}
