package com.renstation.inventory.repository;
import com.renstation.inventory.entity.PlayStationAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PlayStationAccountRepository extends JpaRepository<PlayStationAccount, UUID> {
    List<PlayStationAccount> findByOwnerIdAndDeletedFalse(UUID ownerId);
}
