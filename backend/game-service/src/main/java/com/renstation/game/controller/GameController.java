package com.renstation.game.controller;

import com.renstation.common.dto.PageResponse;
import com.renstation.common.dto.StandardApiResponse;
import com.renstation.game.dto.GameRequest;
import com.renstation.game.entity.Game;
import com.renstation.game.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StandardApiResponse<Game>> createGame(@Valid @RequestBody GameRequest request) {
        Game game = gameService.createGame(request);
        return ResponseEntity.ok(StandardApiResponse.<Game>builder().success(true).data(game).build());
    }

    @GetMapping
    public ResponseEntity<StandardApiResponse<PageResponse<Game>>> getGames(Pageable pageable) {
        Page<Game> page = gameService.getGames(pageable);
        return ResponseEntity.ok(StandardApiResponse.<PageResponse<Game>>builder()
                .success(true).data(PageResponse.of(page)).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardApiResponse<Game>> getGameById(@PathVariable UUID id) {
        Game game = gameService.getGameById(id);
        return ResponseEntity.ok(StandardApiResponse.<Game>builder().success(true).data(game).build());
    }

    @GetMapping("/search")
    public ResponseEntity<StandardApiResponse<PageResponse<Game>>> searchGames(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String platform,
            Pageable pageable) {
        Page<Game> page = gameService.searchGames(keyword, genre, platform, pageable);
        return ResponseEntity.ok(StandardApiResponse.<PageResponse<Game>>builder()
                .success(true).data(PageResponse.of(page)).build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StandardApiResponse<Game>> updateGame(
            @PathVariable UUID id, @Valid @RequestBody GameRequest request) {
        Game game = gameService.updateGame(id, request);
        return ResponseEntity.ok(StandardApiResponse.<Game>builder().success(true).data(game).build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<StandardApiResponse<Void>> deleteGame(@PathVariable UUID id) {
        gameService.deleteGame(id);
        return ResponseEntity.ok(StandardApiResponse.<Void>builder().success(true).build());
    }
}
