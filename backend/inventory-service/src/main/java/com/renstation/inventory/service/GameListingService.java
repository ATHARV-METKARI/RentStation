package com.renstation.inventory.service;

import com.renstation.common.exception.BusinessException;
import com.renstation.common.exception.ResourceNotFoundException;
import com.renstation.inventory.dto.ApprovalRequest;
import com.renstation.inventory.dto.GameListingRequest;
import com.renstation.inventory.entity.GameListing;
import com.renstation.inventory.entity.ListingApproval;
import com.renstation.inventory.entity.PlayStationAccount;
import com.renstation.inventory.repository.GameListingRepository;
import com.renstation.inventory.repository.PlayStationAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameListingService {

    private final GameListingRepository listingRepository;
    private final PlayStationAccountRepository accountRepository;

    @Transactional
    public GameListing createListing(UUID ownerId, GameListingRequest request) {
        PlayStationAccount account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (!account.getOwnerId().equals(ownerId)) {
            throw new BusinessException("You don't own this PSN account");
        }

        listingRepository.findByAccountIdAndGameIdAndListingTypeAndListingStatusAndDeletedFalse(
                account.getId(), request.getGameId(), request.getListingType(), "ACTIVE"
        ).ifPresent(l -> {
            throw new BusinessException("Duplicate active listing exists for this account, game, and type.");
        });

        GameListing listing = GameListing.builder()
                .gameId(request.getGameId())
                .ownerId(ownerId)
                .account(account)
                .listingType(request.getListingType())
                .price(request.getPrice())
                .securityDeposit(request.getSecurityDeposit())
                .availability("AVAILABLE")
                .listingStatus("ACTIVE")
                .approvalStatus("PENDING")
                .conditionNotes(request.getConditionNotes())
                .build();

        return listingRepository.save(listing);
    }

    public List<GameListing> getMyListings(UUID ownerId) {
        return listingRepository.findByOwnerIdAndDeletedFalse(ownerId);
    }

    @Transactional
    public GameListing reviewListing(UUID listingId, UUID expertId, ApprovalRequest request) {
        GameListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found"));

        listing.setApprovalStatus(request.getStatus());
        listing.setExpertId(expertId);
        listing.setApprovedAt(LocalDateTime.now());
        
        return listingRepository.save(listing);
    }
}
