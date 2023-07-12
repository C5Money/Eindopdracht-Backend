package com.example.sparkle.services;

import com.example.sparkle.dtos.inputDto.InventoryInputDto;
import com.example.sparkle.dtos.outputDto.InventoryOutputDto;
import com.example.sparkle.exceptions.ResourceNotFoundException;
import com.example.sparkle.models.Inventory;
import com.example.sparkle.models.Product;
import com.example.sparkle.repositories.InventoryRepository;
import com.example.sparkle.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
//    Instance Variables
    private final InventoryRepository inventoryRepository;

//    Constructor
    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
    }

//    CRUD:
//    ----------------------------------------------------------------------
//    Create
//    ----------------------------------------------------------------------
    public Long createInventoryItem(InventoryInputDto inventoryItemInputDto) {
        Inventory newInventoryEntity = inputDtoToEntity(inventoryItemInputDto);
        inventoryRepository.save(newInventoryEntity);
        return newInventoryEntity.getId();
    }
//    ----------------------------------------------------------------------
//    Read
//    ----------------------------------------------------------------------
    public InventoryOutputDto readOneInventoryItemId(Long id){
        Optional<Inventory> optionalInventoryItem = inventoryRepository.findById(id);
        if(optionalInventoryItem.isEmpty() || id <= 0){
            throw new ResourceNotFoundException("This id: " + id + " is invalid or doesn't exist.");
        }
        return entityToOutputDto(optionalInventoryItem.get());
    }

    public InventoryOutputDto readOneInventoryItemName(String inventoryItemName){
        Inventory foundInventory = inventoryRepository.findByNameContainingIgnoreCase(inventoryItemName).orElseThrow(()-> new ResourceNotFoundException("Inventory item not found."));
        return entityToOutputDto(foundInventory);
    }

    public List<InventoryOutputDto> readAllInventoryItems(){
        List<Inventory> optionalInventoryItemList = inventoryRepository.findAll();
        List<InventoryOutputDto> inventoryOutputDtoList = new ArrayList<>();
        if(optionalInventoryItemList.isEmpty()){
            throw new ResourceNotFoundException("Inventory items list not found.");
        } else {
            for ( Inventory inventoryEntity : optionalInventoryItemList){
                inventoryOutputDtoList.add(entityToOutputDto(inventoryEntity));
            }
        }
        return inventoryOutputDtoList;
    }
//    ----------------------------------------------------------------------
//    Update
//    ----------------------------------------------------------------------
    public InventoryOutputDto updateOneInventoryItem(InventoryInputDto inventoryInputDto, Long id){
        Inventory optionalInventoryItem = inventoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("This inventory item's id: " + id + " does not exist."));
        Inventory updatedInventoryItem = updateInputDtoToEntity(inventoryInputDto, optionalInventoryItem);
        inventoryRepository.save(updatedInventoryItem);
        return entityToOutputDto(updatedInventoryItem);
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    public void deleteOneInventoryItemId(Long id){
        Optional<Inventory> optionalInventoryItem = inventoryRepository.findById(id);
        if(optionalInventoryItem.isEmpty() || id <= 0){
            throw new ResourceNotFoundException("This inventory item's id: " + id + " is already deleted or doesn't exist.");
        }
        inventoryRepository.deleteById(id);
    }

//    MAPPERS:
//    ----------------------------------------------------------------------
//    InputDto to Entity
//    ----------------------------------------------------------------------
    public Inventory inputDtoToEntity(InventoryInputDto inventoryInputDto){
        Inventory inventoryEntity = new Inventory();
        inventoryEntity.setName(inventoryInputDto.name);
        inventoryEntity.setDescription(inventoryInputDto.description);
        inventoryEntity.setQuantity(inventoryInputDto.quantity);
//        inventoryEntity.setProducts(inventoryInputDto.products);
        return inventoryEntity;
    }

    public Inventory updateInputDtoToEntity(InventoryInputDto inventoryInputDto, Inventory inventoryEntity){
        if(inventoryInputDto.id != null){
            inventoryEntity.setId(inventoryInputDto.id);
        }
        if(inventoryInputDto.name != null){
            inventoryEntity.setName(inventoryInputDto.name);
        }
        if(inventoryInputDto.description != null){
            inventoryEntity.setDescription(inventoryInputDto.description);
        }
        if(inventoryInputDto.quantity != null){
            inventoryEntity.setQuantity(inventoryInputDto.quantity);
        }
        return inventoryEntity;
    }
//    ----------------------------------------------------------------------
//    Entity to OutputDto
//    ----------------------------------------------------------------------
    public InventoryOutputDto entityToOutputDto(Inventory inventoryItem){
        InventoryOutputDto inventoryOutputDto = new InventoryOutputDto();
        inventoryOutputDto.id = inventoryItem.getId();
        inventoryOutputDto.name = inventoryItem.getName();
        inventoryOutputDto.description = inventoryItem.getDescription();
        inventoryOutputDto.quantity = inventoryItem.getQuantity();
        return inventoryOutputDto;
    }

}
