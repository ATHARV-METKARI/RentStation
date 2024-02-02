package com.renstation.user.repository;
import com.renstation.user.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserAddressRepository extends JpaRepository<UserAddress, UUID> {
    List<UserAddress> findAllByUserProfile_Id(UUID profileId);
    Optional<UserAddress> findByIdAndUserProfile_Id(UUID id, UUID profileId);
}
