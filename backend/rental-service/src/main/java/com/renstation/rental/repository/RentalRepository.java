package com.renstation.rental.repository;

import com.renstation.rental.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {
    List<Rental> findByRenterId(UUID renterId);
    List<Rental> findByOwnerId(UUID ownerId);
}
