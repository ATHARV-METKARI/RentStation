package com.renstation.admin.controller;

import com.renstation.admin.client.InventoryClient;
import com.renstation.admin.client.ReviewClient;
import com.renstation.admin.client.UserClient;
import com.renstation.common.dto.StandardApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserClient userClient;
    private final InventoryClient inventoryClient;
    private final ReviewClient reviewClient;

    // Users
    @PatchMapping("/users/{id}/status")
    public ResponseEntity<?> updateUserStatus(@PathVariable UUID id, @RequestParam String status) {
        return ResponseEntity.ok(userClient.updateUserStatus(id, status));
    }

    @PatchMapping("/users/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable UUID id, @RequestParam String role) {
        return ResponseEntity.ok(userClient.updateUserRole(id, role));
    }

    // Inventory
    @PatchMapping("/listings/{id}/approval")
    public ResponseEntity<?> updateListingApproval(@PathVariable UUID id, @RequestParam String status) {
        return ResponseEntity.ok(inventoryClient.updateListingApproval(id, status));
    }

    // Reviews
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewClient.deleteReview(id));
    }
}
