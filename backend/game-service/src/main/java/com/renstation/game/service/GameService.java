package com.renstation.game.service;

import com.renstation.common.exception.BusinessException;
import com.renstation.common.exception.ResourceNotFoundException;
import com.renstation.game.dto.GameRequest;
import com.renstation.game.entity.Game;
import com.renstation.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    @Transactional
    public Game createGame(GameRequest request) {
        if (gameRepository.existsByTitleAndPlatformAndDeletedFalse(request.getTitle(), request.getPlatform())) {
            throw new BusinessException("Game with this title and platform already exists.");
        }

        String slug = generateSlug(request.getTitle(), request.getPlatform());

        Game game = Game.builder()
                .title(request.getTitle())
                .slug(slug)
                .platform(request.getPlatform())
                .genre(request.getGenre())
                .publisher(request.getPublisher())
                .developer(request.getDeveloper())
                .releaseDate(request.getReleaseDate())
                .coverImage(request.getCoverImage())
                .thumbnail(request.getThumbnail())
                .description(request.getDescription())
                .tags(request.getTags())
                .build();

        return gameRepository.save(game);
    }

    @Transactional(readOnly = true)
    public Page<Game> getGames(Pageable pageable) {
        return gameRepository.findByDeletedFalse(pageable);
    }

    @Transactional(readOnly = true)
    public Game getGameById(UUID id) {
        return gameRepository.findById(id)
                .filter(g -> !g.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));
    }

    @Transactional(readOnly = true)
    public Page<Game> searchGames(String keyword, String genre, String platform, Pageable pageable) {
        return gameRepository.searchGames(keyword, genre, platform, pageable);
    }

    @Transactional
    public Game updateGame(UUID id, GameRequest request) {
        Game game = getGameById(id);
        
        if (!game.getTitle().equals(request.getTitle()) || !game.getPlatform().equals(request.getPlatform())) {
            if (gameRepository.existsByTitleAndPlatformAndDeletedFalse(request.getTitle(), request.getPlatform())) {
                throw new BusinessException("Game with this title and platform already exists.");
            }
            game.setSlug(generateSlug(request.getTitle(), request.getPlatform()));
        }

        game.setTitle(request.getTitle());
        game.setPlatform(request.getPlatform());
        game.setGenre(request.getGenre());
        game.setPublisher(request.getPublisher());
        game.setDeveloper(request.getDeveloper());
        game.setReleaseDate(request.getReleaseDate());
        game.setCoverImage(request.getCoverImage());
        game.setThumbnail(request.getThumbnail());
        game.setDescription(request.getDescription());
        game.setTags(request.getTags());

        return gameRepository.save(game);
    }

    @Transactional
    public void deleteGame(UUID id) {
        Game game = getGameById(id);
        game.setDeleted(true);
        gameRepository.save(game);
    }

    private String generateSlug(String title, String platform) {
        return (title + "-" + platform).toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("-$", "");
    }
}
