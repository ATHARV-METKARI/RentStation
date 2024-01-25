package com.renstation.inventory.entity;

import com.renstation.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "game_listings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameListing extends BaseEntity {

    @Column(name = "game_id", nullable = false)
    private UUID gameId;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private PlayStationAccount account;

    @Column(name = "listing_type", nullable = false)
    private String listingType;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "security_deposit", nullable = false, precision = 10, scale = 2)
    private BigDecimal securityDeposit;

    @Column(name = "availability", nullable = false)
    private String availability;

    @Column(name = "listing_status", nullable = false)
    private String listingStatus;

    @Column(name = "approval_status", nullable = false)
    private String approvalStatus;

    @Column(name = "condition_notes")
    private String conditionNotes;

    @Column(name = "expert_id")
    private UUID expertId;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;
}
