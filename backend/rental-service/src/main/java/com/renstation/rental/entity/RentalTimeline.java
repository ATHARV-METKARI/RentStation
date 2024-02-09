package com.renstation.rental.entity;

import com.renstation.common.entity.UUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rental_timelines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalTimeline extends UUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id", nullable = false)
    private Rental rental;

    @Column(name = "status_from")
    private String statusFrom;

    @Column(name = "status_to", nullable = false)
    private String statusTo;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
