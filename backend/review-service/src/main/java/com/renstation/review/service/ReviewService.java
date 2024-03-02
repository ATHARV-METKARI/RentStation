package com.renstation.review.service;

import com.renstation.common.exception.BusinessException;
import com.renstation.review.client.RentalClient;
import com.renstation.review.dto.RentalDto;
import com.renstation.review.dto.ReviewRequest;
import com.renstation.review.entity.RatingAggregate;
import com.renstation.review.entity.Review;
import com.renstation.review.repository.RatingAggregateRepository;
import com.renstation.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RatingAggregateRepository aggregateRepository;
    private final RentalClient rentalClient;

    @Transactional
    public Review createReview(UUID reviewerId, ReviewRequest request) {
        
        // 1. Anti-Spam Check
        if (reviewRepository.existsByRentalIdAndReviewerIdAndDeletedFalse(request.getRentalId(), reviewerId)) {
            throw new BusinessException("You have already reviewed this rental transaction.");
        }

        // 2. Fetch Rental and Validate Status
        RentalDto rental;
        try {
            rental = rentalClient.getRentalById(request.getRentalId()).getData();
        } catch (Exception e) {
            throw new BusinessException("Invalid rental ID or rental service unavailable.");
        }

        if (!"COMPLETED".equals(rental.getStatus())) {
            throw new BusinessException("Reviews can only be left for COMPLETED rentals.");
        }

        // 3. Ensure user was part of the rental
        if (!reviewerId.equals(rental.getOwnerId()) && !reviewerId.equals(rental.getRenterId())) {
            throw new BusinessException("You were not part of this rental transaction.");
        }

        Review review = Review.builder()
                .rentalId(request.getRentalId())
                .reviewerId(reviewerId)
                .reviewedUserId(request.getReviewedUserId())
                .listingId(request.getListingId())
                .rating(request.getRating())
                .comment(request.getComment())
                .reviewType(request.getReviewType())
                .build();
        
        review = reviewRepository.save(review);
        
        // 4. Update Aggregates (could be async in future)
        if (request.getReviewedUserId() != null) {
            updateAggregate(request.getReviewedUserId(), "USER", request.getRating());
        }
        if (request.getListingId() != null) {
            updateAggregate(request.getListingId(), "LISTING", request.getRating());
        }

        return review;
    }

    private void updateAggregate(UUID targetId, String targetType, Integer newRating) {
        RatingAggregate aggregate = aggregateRepository.findByTargetIdAndDeletedFalse(targetId)
                .orElseGet(() -> RatingAggregate.builder()
                        .targetId(targetId)
                        .targetType(targetType)
                        .averageRating(BigDecimal.ZERO)
                        .totalReviews(0)
                        .build());

        int total = aggregate.getTotalReviews() + 1;
        BigDecimal currentSum = aggregate.getAverageRating().multiply(new BigDecimal(aggregate.getTotalReviews()));
        BigDecimal newSum = currentSum.add(new BigDecimal(newRating));
        BigDecimal newAvg = newSum.divide(new BigDecimal(total), 2, RoundingMode.HALF_UP);

        aggregate.setTotalReviews(total);
        aggregate.setAverageRating(newAvg);
        aggregateRepository.save(aggregate);
    }

    public Page<Review> getUserReviews(UUID userId, Pageable pageable) {
        return reviewRepository.findByReviewedUserIdAndDeletedFalse(userId, pageable);
    }

    public Page<Review> getListingReviews(UUID listingId, Pageable pageable) {
        return reviewRepository.findByListingIdAndDeletedFalse(listingId, pageable);
    }
}
