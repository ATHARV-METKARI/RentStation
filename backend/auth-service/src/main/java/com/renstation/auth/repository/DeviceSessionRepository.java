package com.renstation.auth.repository;
import com.renstation.auth.entity.DeviceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface DeviceSessionRepository extends JpaRepository<DeviceSession, UUID> {
    Optional<DeviceSession> findByDeviceIdAndUser_Id(String deviceId, UUID userId);
}
