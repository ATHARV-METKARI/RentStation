package com.renstation.gameservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "games", indexes = {
    @Index(name = "idx_game_title", columnList = "title")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 50)
    private String platform;

    @Column(length = 100)
    private String genre;

    @Column(length = 100)
    private String publisher;

    @Column(length = 100)
    private String developer;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "pegi_rating", length = 10)
    private String pegiRating;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
