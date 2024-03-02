package com.renstation.review.repository;
import com.renstation.review.entity.RatingAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface RatingAggregateRepository extends JpaRepository<RatingAggregate, UUID> {
    Optional<RatingAggregate> findByTargetIdAndDeletedFalse(UUID targetId);
}
