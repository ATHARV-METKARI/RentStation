package com.renstation.rental.service;

import com.renstation.common.exception.BusinessException;
import com.renstation.common.exception.ResourceNotFoundException;
import com.renstation.rental.client.InventoryClient;
import com.renstation.rental.dto.RentalRequest;
import com.renstation.rental.entity.Rental;
import com.renstation.rental.entity.RentalTimeline;
import com.renstation.rental.repository.RentalRepository;
import com.renstation.rental.repository.RentalTimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final RentalTimelineRepository timelineRepository;
    private final InventoryClient inventoryClient;

    @Transactional
    public Rental createRental(UUID renterId, RentalRequest request) {
        if (request.getOwnerId().equals(renterId)) {
            throw new BusinessException("You cannot rent your own listing.");
        }

        // Lock inventory via synchronous Feign Call
        try {
            inventoryClient.updateListingStatus(request.getListingId(), Map.of("status", "RESERVED"));
        } catch (Exception e) {
            throw new BusinessException("Listing is no longer available.");
        }

        Rental rental = Rental.builder()
                .listingId(request.getListingId())
                .gameId(request.getGameId())
                .ownerId(request.getOwnerId())
                .renterId(renterId)
                .rentalType(request.getRentalType())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .price(request.getPrice())
                .deposit(request.getDeposit())
                .status("REQUESTED")
                .paymentStatus("PENDING")
                .build();
        
        rental = rentalRepository.save(rental);
        
        addTimeline(rental, null, "REQUESTED", "Rental created successfully.");

        return rental;
    }

    public List<Rental> getMyRentals(UUID renterId) {
        return rentalRepository.findByRenterId(renterId);
    }
    
    public Rental getRentalById(UUID id) {
        return rentalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rental not found"));
    }

    @Transactional
    public void changeStatus(UUID id, String newStatus, String remarks) {
        Rental rental = getRentalById(id);
        String oldStatus = rental.getStatus();
        
        // Complex State Machine rules would go here
        rental.setStatus(newStatus);
        rentalRepository.save(rental);
        
        addTimeline(rental, oldStatus, newStatus, remarks);
    }

    private void addTimeline(Rental rental, String from, String to, String remarks) {
        RentalTimeline timeline = RentalTimeline.builder()
                .rental(rental)
                .statusFrom(from)
                .statusTo(to)
                .remarks(remarks)
                .createdAt(LocalDateTime.now())
                .build();
        timelineRepository.save(timeline);
    }
}
