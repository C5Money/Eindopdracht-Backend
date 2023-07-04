package com.example.sparkle.repositories;

import com.example.sparkle.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {



}
