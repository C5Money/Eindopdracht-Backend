package com.example.sparkle.controllers;

import com.example.sparkle.services.InventoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    //    Instance Variables
    private final InventoryService inventoryService;
    //    Constructor
    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

}
