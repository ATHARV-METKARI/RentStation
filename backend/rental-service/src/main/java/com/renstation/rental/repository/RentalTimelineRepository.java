package com.renstation.rental.repository;
import com.renstation.rental.entity.RentalTimeline;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RentalTimelineRepository extends JpaRepository<RentalTimeline, UUID> {
}
