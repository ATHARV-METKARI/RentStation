package com.renstation.user.entity;

import com.renstation.common.entity.UUIDEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "user_statistics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStatistics extends UUIDEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", nullable = false, unique = true)
    private UserProfile userProfile;

    @Column(name = "seller_rating", precision = 3, scale = 2)
    private BigDecimal sellerRating;

    @Column(name = "completed_rentals", nullable = false)
    private Integer completedRentals;

    @Column(name = "cancelled_rentals", nullable = false)
    private Integer cancelledRentals;

    @Column(name = "total_earnings", precision = 10, scale = 2)
    private BigDecimal totalEarnings;

    @Column(name = "expert_disputes_resolved")
    private Integer expertDisputesResolved;
}
