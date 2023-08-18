package com.example.sparkle.controllers;

import com.example.sparkle.dtos.inputDto.InventoryInputDto;
import com.example.sparkle.dtos.outputDto.InventoryOutputDto;
import com.example.sparkle.services.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/inventories")
public class InventoryController {
//    Instance Variables
    private final InventoryService inventoryService;
//    Constructor
    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }
//    MAPPINGS:
//    ----------------------------------------------------------------------
//    Post
//    ----------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<Object> createInventoryItem(@Valid @RequestBody InventoryInputDto inventoryInputDto, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                stringBuilder.append(fieldError.getField() + ": ");
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        }
        Long newInventoryDto = inventoryService.createInventoryItem(inventoryInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newInventoryDto ).toUriString());
        inventoryInputDto.id = newInventoryDto;
        return ResponseEntity.created(uri).body("Inventory item with id: " + newInventoryDto + " is succesfully created.");
    }
//    ----------------------------------------------------------------------
//    Get
//    ----------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<InventoryOutputDto> readOneInventoryItemById(@PathVariable Long id){
        InventoryOutputDto inventoryOutputDto = inventoryService.readOneInventoryItemId(id);
        return ResponseEntity.ok().body(inventoryOutputDto);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<InventoryOutputDto> readOneInventoryItemByName(@PathVariable String name){
        InventoryOutputDto inventoryOutputDto = inventoryService.readOneInventoryItemName(name);
        return ResponseEntity.ok().body(inventoryOutputDto);
    }

    @GetMapping
    public ResponseEntity<List<InventoryOutputDto>> readAllInventoryItems(){
        List<InventoryOutputDto> inventoryDtoList = inventoryService.readAllInventoryItems();
        return ResponseEntity.ok().body(inventoryDtoList);
    }
//    ----------------------------------------------------------------------
//    Put
//    ----------------------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOneInventoryItem(@Valid @RequestBody InventoryInputDto inventoryInputDto, @PathVariable Long id, BindingResult bindingResult){
        InventoryOutputDto inventoryOutputDto = inventoryService.updateOneInventoryItem(inventoryInputDto, id);
        if(bindingResult.hasFieldErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                stringBuilder.append(fieldError.getField() + ": ");
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        }
        return ResponseEntity.accepted().body(inventoryOutputDto);
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOneInventoryItemById(@PathVariable Long id){
        inventoryService.deleteOneInventoryItemId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
