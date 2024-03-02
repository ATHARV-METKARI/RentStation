package com.renstation.review.controller;

import com.renstation.common.dto.PageResponse;
import com.renstation.common.dto.StandardApiResponse;
import com.renstation.review.dto.ReviewRequest;
import com.renstation.review.entity.Review;
import com.renstation.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<StandardApiResponse<Review>> createReview(
            Authentication auth, @Valid @RequestBody ReviewRequest request) {
        Review review = reviewService.createReview(UUID.fromString(auth.getName()), request);
        return ResponseEntity.ok(StandardApiResponse.<Review>builder().success(true).data(review).build());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<StandardApiResponse<PageResponse<Review>>> getUserReviews(
            @PathVariable UUID id, Pageable pageable) {
        Page<Review> page = reviewService.getUserReviews(id, pageable);
        return ResponseEntity.ok(StandardApiResponse.<PageResponse<Review>>builder()
                .success(true).data(PageResponse.of(page)).build());
    }

    @GetMapping("/listing/{id}")
    public ResponseEntity<StandardApiResponse<PageResponse<Review>>> getListingReviews(
            @PathVariable UUID id, Pageable pageable) {
        Page<Review> page = reviewService.getListingReviews(id, pageable);
        return ResponseEntity.ok(StandardApiResponse.<PageResponse<Review>>builder()
                .success(true).data(PageResponse.of(page)).build());
    }
}
