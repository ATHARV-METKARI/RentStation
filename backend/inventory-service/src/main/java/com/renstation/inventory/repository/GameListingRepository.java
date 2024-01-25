package com.renstation.inventory.repository;
import com.renstation.inventory.entity.GameListing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameListingRepository extends JpaRepository<GameListing, UUID> {
    List<GameListing> findByOwnerIdAndDeletedFalse(UUID ownerId);
    Optional<GameListing> findByAccountIdAndGameIdAndListingTypeAndListingStatusAndDeletedFalse(
        UUID accountId, UUID gameId, String listingType, String listingStatus);
}
