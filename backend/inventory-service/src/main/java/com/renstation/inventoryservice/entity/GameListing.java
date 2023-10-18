package com.renstation.inventoryservice.entity;

import com.renstation.inventoryservice.enums.ListingStatus;
import com.renstation.inventoryservice.enums.RentalType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "game_listings", indexes = {
    @Index(name = "idx_listing_owner", columnList = "owner_id"),
    @Index(name = "idx_listing_game", columnList = "game_id"),
    @Index(name = "idx_listing_status", columnList = "listing_status"),
    @Index(name = "idx_listing_type", columnList = "rental_type")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameListing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "game_id", nullable = false)
    private UUID gameId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "playstation_account_id", nullable = false)
    private PlayStationAccount playStationAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "rental_type", nullable = false, length = 20)
    private RentalType rentalType;

    @Column(name = "rental_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal rentalPrice;

    @Column(name = "security_deposit", nullable = false, precision = 10, scale = 2)
    private BigDecimal securityDeposit;

    @Column(nullable = false)
    private boolean availability;

    @Enumerated(EnumType.STRING)
    @Column(name = "listing_status", nullable = false, length = 30)
    private ListingStatus listingStatus;

    @Column(name = "expert_approval_status", length = 30)
    private String expertApprovalStatus;

    @Column(name = "expert_remarks", columnDefinition = "TEXT")
    private String expertRemarks;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
