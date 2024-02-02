package com.renstation.user.repository;
import com.renstation.user.entity.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserStatisticsRepository extends JpaRepository<UserStatistics, UUID> {
    Optional<UserStatistics> findByUserProfile_Id(UUID profileId);
}
