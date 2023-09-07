package com.eindopdracht.sparkle.repositories;

import com.eindopdracht.sparkle.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByNameContainingIgnoreCase(String name);

}
