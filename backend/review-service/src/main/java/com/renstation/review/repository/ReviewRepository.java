package com.renstation.review.repository;

import com.renstation.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Page<Review> findByReviewedUserIdAndDeletedFalse(UUID reviewedUserId, Pageable pageable);
    Page<Review> findByListingIdAndDeletedFalse(UUID listingId, Pageable pageable);
    boolean existsByRentalIdAndReviewerIdAndDeletedFalse(UUID rentalId, UUID reviewerId);
}
