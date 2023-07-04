package com.example.sparkle.services;

import com.example.sparkle.repositories.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
//    Instance Variables
    private final InventoryRepository inventoryRepository;
//    Constructor
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
//    CRUD:
//    ----------------------------------------------------------------------
//    Create
//    ----------------------------------------------------------------------

}
