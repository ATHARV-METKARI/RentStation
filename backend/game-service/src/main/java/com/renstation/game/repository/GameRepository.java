package com.renstation.game.repository;

import com.renstation.game.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
    
    Optional<Game> findBySlugAndDeletedFalse(String slug);
    
    boolean existsByTitleAndPlatformAndDeletedFalse(String title, String platform);

    Page<Game> findByDeletedFalse(Pageable pageable);

    @Query("SELECT g FROM Game g WHERE g.deleted = false AND " +
           "(:keyword IS NULL OR LOWER(g.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(g.tags) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:genre IS NULL OR g.genre = :genre) AND " +
           "(:platform IS NULL OR g.platform = :platform)")
    Page<Game> searchGames(@Param("keyword") String keyword, 
                           @Param("genre") String genre, 
                           @Param("platform") String platform, 
                           Pageable pageable);
}
