package com.renstation.inventory.controller;

import com.renstation.common.dto.StandardApiResponse;
import com.renstation.inventory.dto.ApprovalRequest;
import com.renstation.inventory.dto.GameListingRequest;
import com.renstation.inventory.entity.GameListing;
import com.renstation.inventory.service.GameListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/listings")
@RequiredArgsConstructor
public class GameListingController {

    private final GameListingService listingService;

    @PostMapping
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<StandardApiResponse<GameListing>> createListing(
            Authentication auth, @Valid @RequestBody GameListingRequest request) {
        GameListing listing = listingService.createListing(UUID.fromString(auth.getName()), request);
        return ResponseEntity.ok(StandardApiResponse.<GameListing>builder()
                .success(true).data(listing).build());
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<StandardApiResponse<List<GameListing>>> getMyListings(Authentication auth) {
        List<GameListing> listings = listingService.getMyListings(UUID.fromString(auth.getName()));
        return ResponseEntity.ok(StandardApiResponse.<List<GameListing>>builder()
                .success(true).data(listings).build());
    }

    @PatchMapping("/{id}/approval")
    @PreAuthorize("hasAuthority('EXPERT') or hasAuthority('ADMIN')")
    public ResponseEntity<StandardApiResponse<GameListing>> reviewListing(
            @PathVariable UUID id, Authentication auth, @Valid @RequestBody ApprovalRequest request) {
        GameListing listing = listingService.reviewListing(id, UUID.fromString(auth.getName()), request);
        return ResponseEntity.ok(StandardApiResponse.<GameListing>builder()
                .success(true).data(listing).build());
    }
}
