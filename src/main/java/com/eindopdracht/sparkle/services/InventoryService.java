package com.eindopdracht.sparkle.services;

import com.eindopdracht.sparkle.dtos.inputDto.InventoryInputDto;
import com.eindopdracht.sparkle.exceptions.ResourceNotFoundException;
import com.eindopdracht.sparkle.dtos.outputDto.InventoryOutputDto;
import com.eindopdracht.sparkle.models.Inventory;
import com.eindopdracht.sparkle.repositories.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Long createInventoryItem(InventoryInputDto inventoryItemInputDto) {
        Inventory newInventoryItem = inputDtoToEntity(inventoryItemInputDto);
        inventoryRepository.save(newInventoryItem);
        return newInventoryItem.getId();
    }
//    ----------------------------------------------------------------------
//    Read
//    ----------------------------------------------------------------------
    public InventoryOutputDto readOneInventoryItemId(Long id){
        Optional<Inventory> optionalInventoryItem = inventoryRepository.findById(id);
        if(optionalInventoryItem.isEmpty()){
            throw new ResourceNotFoundException("This  id: " + id + " is invalid or doesn't exist.");
        }
        return entityToOutputDto(optionalInventoryItem.get());
    }

    public InventoryOutputDto readOneInventoryItemName(String inventoryItemName){
        Inventory foundInventory = inventoryRepository.findByNameContainingIgnoreCase(inventoryItemName).orElseThrow(()-> new ResourceNotFoundException("Inventory item not found."));
        return entityToOutputDto(foundInventory);
    }

    public List<InventoryOutputDto> readAllInventoryItems(){
        List<Inventory> inventoryItemList = inventoryRepository.findAll();
        List<InventoryOutputDto> inventoryOutputDtoList = new ArrayList<>();
        if(inventoryItemList.isEmpty()){
            throw new ResourceNotFoundException("Inventory items not found.");
        } else {
            for ( Inventory inventoryEntity : inventoryItemList){
                inventoryOutputDtoList.add(entityToOutputDto(inventoryEntity));
            }
        }
        return inventoryOutputDtoList;
    }
//    ----------------------------------------------------------------------
//    Update
//    ----------------------------------------------------------------------
    public InventoryOutputDto updateOneInventoryItem(InventoryInputDto inventoryInputDto, Long id){
        Optional<Inventory> optionalInventoryItem = inventoryRepository.findById(id);
        if(optionalInventoryItem.isPresent()){
            Inventory updatableInventoryItem = optionalInventoryItem.get();
            Inventory updatedInventoryItem = updateInputDtoToEntity(inventoryInputDto, updatableInventoryItem);

            inventoryRepository.save(updatedInventoryItem);
            return entityToOutputDto(updatedInventoryItem);
        } else {
            throw new ResourceNotFoundException("This inventory item's id: " + id + " does not exist.");
        }
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    public void deleteOneInventoryItemId(Long id){
        Optional<Inventory> optionalInventoryItem = inventoryRepository.findById(id);
        if(optionalInventoryItem.isEmpty()){
            throw new ResourceNotFoundException("This inventory item's id: " + id + " is already deleted or doesn't exist.");
        }
        Inventory foundInventoryItem = optionalInventoryItem.get();
        inventoryRepository.delete(foundInventoryItem);
    }

//    MAPPERS:
//    ----------------------------------------------------------------------
//    InputDto to Entity
//    ----------------------------------------------------------------------
    public Inventory inputDtoToEntity(InventoryInputDto inventoryInputDto){
        Inventory inventoryEntity = new Inventory();
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
        inventoryOutputDto.products = inventoryItem.getProducts();
        return inventoryOutputDto;
    }

}
