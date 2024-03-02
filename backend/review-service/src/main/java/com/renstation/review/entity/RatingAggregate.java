package com.renstation.review.entity;

import com.renstation.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "rating_aggregates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingAggregate extends BaseEntity {

    @Column(name = "target_id", nullable = false, unique = true)
    private UUID targetId;

    @Column(name = "target_type", nullable = false)
    private String targetType; // USER or LISTING

    @Column(name = "average_rating", nullable = false, precision = 3, scale = 2)
    private BigDecimal averageRating;

    @Column(name = "total_reviews", nullable = false)
    private Integer totalReviews;
}
