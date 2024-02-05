package com.renstation.game.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class GameRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String platform;
    @NotBlank
    private String genre;
    private String publisher;
    private String developer;
    @NotNull
    private LocalDate releaseDate;
    private String coverImage;
    private String thumbnail;
    private String description;
    private String tags;
}
