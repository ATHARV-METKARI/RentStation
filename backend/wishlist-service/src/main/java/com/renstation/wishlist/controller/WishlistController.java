package com.renstation.wishlist.controller;

import com.renstation.common.dto.StandardApiResponse;
import com.renstation.wishlist.dto.WishlistItemRequest;
import com.renstation.wishlist.entity.Wishlist;
import com.renstation.wishlist.service.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wishlists")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<StandardApiResponse<Wishlist>> getMyWishlist(Authentication auth) {
        Wishlist wishlist = wishlistService.getMyWishlist(UUID.fromString(auth.getName()));
        return ResponseEntity.ok(StandardApiResponse.<Wishlist>builder().success(true).data(wishlist).build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<StandardApiResponse<Wishlist>> addItem(
            Authentication auth, @Valid @RequestBody WishlistItemRequest request) {
        Wishlist wishlist = wishlistService.addItem(UUID.fromString(auth.getName()), request);
        return ResponseEntity.ok(StandardApiResponse.<Wishlist>builder().success(true).data(wishlist).build());
    }

    @DeleteMapping("/{itemId}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<StandardApiResponse<Void>> removeItem(
            Authentication auth, @PathVariable UUID itemId) {
        wishlistService.removeItem(UUID.fromString(auth.getName()), itemId);
        return ResponseEntity.ok(StandardApiResponse.<Void>builder().success(true).build());
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<StandardApiResponse<Void>> clearWishlist(Authentication auth) {
        wishlistService.clearWishlist(UUID.fromString(auth.getName()));
        return ResponseEntity.ok(StandardApiResponse.<Void>builder().success(true).build());
    }
}
