package com.renstation.rental.controller;

import com.renstation.common.dto.StandardApiResponse;
import com.renstation.rental.dto.RentalRequest;
import com.renstation.rental.entity.Rental;
import com.renstation.rental.service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<StandardApiResponse<Rental>> createRental(
            Authentication auth, @Valid @RequestBody RentalRequest request) {
        Rental rental = rentalService.createRental(UUID.fromString(auth.getName()), request);
        return ResponseEntity.ok(StandardApiResponse.<Rental>builder().success(true).data(rental).build());
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<StandardApiResponse<List<Rental>>> getMyRentals(Authentication auth) {
        List<Rental> rentals = rentalService.getMyRentals(UUID.fromString(auth.getName()));
        return ResponseEntity.ok(StandardApiResponse.<List<Rental>>builder().success(true).data(rentals).build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENT') or hasAuthority('EXPERT')")
    public ResponseEntity<StandardApiResponse<Rental>> getRentalById(@PathVariable UUID id) {
        Rental rental = rentalService.getRentalById(id);
        return ResponseEntity.ok(StandardApiResponse.<Rental>builder().success(true).data(rental).build());
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StandardApiResponse<Void>> updateStatus(
            @PathVariable UUID id, @RequestParam String status, @RequestParam(required = false) String remarks) {
        rentalService.changeStatus(id, status, remarks);
        return ResponseEntity.ok(StandardApiResponse.<Void>builder().success(true).message("Status updated").build());
    }
}
