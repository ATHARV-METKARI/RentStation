package com.renstation.game.entity;

import com.renstation.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "platform", nullable = false)
    private String platform;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "developer")
    private String developer;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "rating", precision = 3, scale = 2)
    private BigDecimal rating;

    @Column(name = "tags")
    private String tags;
}
