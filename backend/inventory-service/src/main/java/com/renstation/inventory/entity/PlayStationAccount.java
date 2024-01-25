package com.renstation.inventory.entity;

import com.renstation.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "playstation_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayStationAccount extends BaseEntity {

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "psn_online_id", nullable = false)
    private String psnOnlineId;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "verification_status", nullable = false)
    private String verificationStatus;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "expert_id")
    private UUID expertId;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "status", nullable = false)
    private String status;
}
