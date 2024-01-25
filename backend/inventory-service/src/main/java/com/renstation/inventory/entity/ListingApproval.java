package com.renstation.inventory.entity;

import com.renstation.common.entity.UUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "listing_approvals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListingApproval extends UUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    private GameListing listing;

    @Column(name = "expert_id", nullable = false)
    private UUID expertId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "approved_at", nullable = false)
    private LocalDateTime approvedAt;
}
