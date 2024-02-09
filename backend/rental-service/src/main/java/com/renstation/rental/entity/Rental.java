package com.renstation.rental.entity;

import com.renstation.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rentals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental extends BaseEntity {

    @Column(name = "listing_id", nullable = false)
    private UUID listingId;

    @Column(name = "game_id", nullable = false)
    private UUID gameId;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "renter_id", nullable = false)
    private UUID renterId;

    @Column(name = "rental_type", nullable = false)
    private String rentalType;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "deposit", nullable = false, precision = 10, scale = 2)
    private BigDecimal deposit;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;
}
