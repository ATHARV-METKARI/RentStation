package com.renstation.inventoryservice.entity;

import com.renstation.inventoryservice.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "playstation_accounts", indexes = {
    @Index(name = "idx_ps_acc_owner", columnList = "owner_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayStationAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "psn_online_id", nullable = false, length = 100)
    private String psnOnlineId;

    @Column(length = 50)
    private String region;

    @Column(length = 50)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false, length = 30)
    private AccountStatus accountStatus;

    @Column(name = "expert_verified", nullable = false)
    private boolean expertVerified;

    @Column(name = "submitted_date")
    private LocalDateTime submittedDate;

    @Column(name = "approved_date")
    private LocalDateTime approvedDate;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "playStationAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GameListing> listings;
}
