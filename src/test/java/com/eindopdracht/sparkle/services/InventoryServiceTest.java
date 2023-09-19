package com.eindopdracht.sparkle.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eindopdracht.sparkle.dtos.inputDto.InventoryInputDto;
import com.eindopdracht.sparkle.dtos.outputDto.InventoryOutputDto;
import com.eindopdracht.sparkle.exceptions.ResourceNotFoundException;
import com.eindopdracht.sparkle.models.Inventory;
import com.eindopdracht.sparkle.models.Product;
import com.eindopdracht.sparkle.repositories.InventoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {InventoryService.class})
@ExtendWith(SpringExtension.class)
class InventoryServiceTest {
    @MockBean
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryService inventoryService;


    @Test
    void testCreateInventoryItem() {
        Inventory inventory = new Inventory();
        inventory.setDescription("The characteristics of someone or something");
        inventory.setId(1L);
        inventory.setName("Name");
        inventory.setProducts(new ArrayList<>());
        inventory.setQuantity(1);
        when(inventoryRepository.save(Mockito.<Inventory>any())).thenReturn(inventory);
        assertEquals(1L,
                inventoryService
                        .createInventoryItem(new InventoryInputDto(1L, "Name", "The characteristics of someone or something", 1))
                        .longValue());
        verify(inventoryRepository).save(Mockito.<Inventory>any());
    }



    @Test
    void testReadOneInventoryItemId() {
        Inventory inventory = new Inventory();
        inventory.setDescription("The characteristics of someone or something");
        inventory.setId(1L);
        inventory.setName("Name");
        inventory.setProducts(new ArrayList<>());
        inventory.setQuantity(1);
        Optional<Inventory> ofResult = Optional.of(inventory);
        when(inventoryRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        InventoryOutputDto actualReadOneInventoryItemIdResult = inventoryService.readOneInventoryItemId(1L);
        assertEquals("The characteristics of someone or something", actualReadOneInventoryItemIdResult.description);
        assertEquals(1, actualReadOneInventoryItemIdResult.quantity.intValue());
        assertTrue(actualReadOneInventoryItemIdResult.products.isEmpty());
        assertEquals("Name", actualReadOneInventoryItemIdResult.name);
        assertEquals(1L, actualReadOneInventoryItemIdResult.id.longValue());
        verify(inventoryRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testReadOneInventoryItemName() {
        Inventory inventory = new Inventory();
        inventory.setDescription("The characteristics of someone or something");
        inventory.setId(1L);
        inventory.setName("Name");
        inventory.setProducts(new ArrayList<>());
        inventory.setQuantity(1);
        Optional<Inventory> ofResult = Optional.of(inventory);
        when(inventoryRepository.findByNameContainingIgnoreCase(Mockito.<String>any())).thenReturn(ofResult);
        InventoryOutputDto actualReadOneInventoryItemNameResult = inventoryService
                .readOneInventoryItemName("Inventory Item Name");
        assertEquals("The characteristics of someone or something", actualReadOneInventoryItemNameResult.description);
        assertEquals(1, actualReadOneInventoryItemNameResult.quantity.intValue());
        assertTrue(actualReadOneInventoryItemNameResult.products.isEmpty());
        assertEquals("Name", actualReadOneInventoryItemNameResult.name);
        assertEquals(1L, actualReadOneInventoryItemNameResult.id.longValue());
        verify(inventoryRepository).findByNameContainingIgnoreCase(Mockito.<String>any());
    }


    @Test
    void testReadAllInventoryItems() {
        Inventory inventory = new Inventory();
        inventory.setDescription("The characteristics of someone or something");
        inventory.setId(1L);
        inventory.setName("Inventory items not found.");
        inventory.setProducts(new ArrayList<>());
        inventory.setQuantity(1);

        ArrayList<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(inventory);
        when(inventoryRepository.findAll()).thenReturn(inventoryList);
        List<InventoryOutputDto> actualReadAllInventoryItemsResult = inventoryService.readAllInventoryItems();
        assertEquals(1, actualReadAllInventoryItemsResult.size());
        InventoryOutputDto getResult = actualReadAllInventoryItemsResult.get(0);
        assertEquals("The characteristics of someone or something", getResult.description);
        assertEquals(1, getResult.quantity.intValue());
        assertTrue(getResult.products.isEmpty());
        assertEquals("Inventory items not found.", getResult.name);
        assertEquals(1L, getResult.id.longValue());
        verify(inventoryRepository).findAll();
    }


    @Test
    void testUpdateOneInventoryItem() {
        Inventory inventory = new Inventory();
        inventory.setDescription("The characteristics of someone or something");
        inventory.setId(1L);
        inventory.setName("Name");
        inventory.setProducts(new ArrayList<>());
        inventory.setQuantity(1);
        Optional<Inventory> ofResult = Optional.of(inventory);

        Inventory inventory2 = new Inventory();
        inventory2.setDescription("The characteristics of someone or something");
        inventory2.setId(1L);
        inventory2.setName("Name");
        ArrayList<Product> products = new ArrayList<>();
        inventory2.setProducts(products);
        inventory2.setQuantity(1);
        when(inventoryRepository.save(Mockito.<Inventory>any())).thenReturn(inventory2);
        when(inventoryRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        InventoryOutputDto actualUpdateOneInventoryItemResult = inventoryService.updateOneInventoryItem(
                new InventoryInputDto(1L, "Name", "The characteristics of someone or something", 1), 1L);
        assertEquals("The characteristics of someone or something", actualUpdateOneInventoryItemResult.description);
        assertEquals(1, actualUpdateOneInventoryItemResult.quantity.intValue());
        assertEquals(products, actualUpdateOneInventoryItemResult.products);
        assertEquals("Name", actualUpdateOneInventoryItemResult.name);
        assertEquals(1L, actualUpdateOneInventoryItemResult.id.longValue());
        verify(inventoryRepository).save(Mockito.<Inventory>any());
        verify(inventoryRepository).findById(Mockito.<Long>any());
    }



    @Test
    void testDeleteOneInventoryItemId() {
        Inventory inventory = new Inventory();
        inventory.setDescription("The characteristics of someone or something");
        inventory.setId(1L);
        inventory.setName("Name");
        inventory.setProducts(new ArrayList<>());
        inventory.setQuantity(1);
        Optional<Inventory> ofResult = Optional.of(inventory);
        doNothing().when(inventoryRepository).delete(Mockito.<Inventory>any());
        when(inventoryRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        inventoryService.deleteOneInventoryItemId(1L);
        verify(inventoryRepository).findById(Mockito.<Long>any());
        verify(inventoryRepository).delete(Mockito.<Inventory>any());
    }
}

