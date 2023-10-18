package com.renstation.rentalservice.entity;

import com.renstation.rentalservice.enums.RentalStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rentals", indexes = {
    @Index(name = "idx_rental_listing", columnList = "listing_id"),
    @Index(name = "idx_rental_renter", columnList = "renter_id"),
    @Index(name = "idx_rental_owner", columnList = "owner_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "listing_id", nullable = false)
    private UUID listingId;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "renter_id", nullable = false)
    private UUID renterId;

    @Column(name = "rental_start")
    private LocalDateTime rentalStart;

    @Column(name = "rental_end")
    private LocalDateTime rentalEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "rental_status", nullable = false, length = 30)
    private RentalStatus rentalStatus;

    @Column(name = "return_status", length = 30)
    private String returnStatus;

    @Column(name = "payment_status", length = 30)
    private String paymentStatus;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
