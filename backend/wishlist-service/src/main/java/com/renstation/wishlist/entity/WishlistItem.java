package com.renstation.wishlist.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.renstation.common.entity.UUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "wishlist_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistItem extends UUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist_id", nullable = false)
    @JsonBackReference
    private Wishlist wishlist;

    @Column(name = "game_id")
    private UUID gameId;

    @Column(name = "listing_id")
    private UUID listingId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
