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
    void testCreateInventoryItem2() {
        Inventory inventory = new Inventory();
        inventory.setDescription("The characteristics of someone or something");
        inventory.setId(1L);
        inventory.setName("Name");
        inventory.setProducts(new ArrayList<>());
        inventory.setQuantity(1);
        when(inventoryRepository.save(Mockito.<Inventory>any())).thenReturn(inventory);
        assertNull(inventoryService
                .createInventoryItem(new InventoryInputDto(null, "Name", "The characteristics of someone or something", 1)));
        verify(inventoryRepository).save(Mockito.<Inventory>any());
    }


    @Test
    void testCreateInventoryItem3() {
        Inventory inventory = new Inventory();
        inventory.setDescription("The characteristics of someone or something");
        inventory.setId(1L);
        inventory.setName("Name");
        inventory.setProducts(new ArrayList<>());
        inventory.setQuantity(1);
        when(inventoryRepository.save(Mockito.<Inventory>any())).thenReturn(inventory);
        assertEquals(1L,
                inventoryService
                        .createInventoryItem(new InventoryInputDto(1L, null, "The characteristics of someone or something", 1))
                        .longValue());
        verify(inventoryRepository).save(Mockito.<Inventory>any());
    }


    @Test
    void testCreateInventoryItem4() {
        Inventory inventory = new Inventory();
        inventory.setDescription("The characteristics of someone or something");
        inventory.setId(1L);
        inventory.setName("Name");
        inventory.setProducts(new ArrayList<>());
        inventory.setQuantity(1);
        when(inventoryRepository.save(Mockito.<Inventory>any())).thenReturn(inventory);
        assertEquals(1L, inventoryService.createInventoryItem(new InventoryInputDto(1L, "Name", null, 1)).longValue());
        verify(inventoryRepository).save(Mockito.<Inventory>any());
    }


    @Test
    void testCreateInventoryItem5() {
        Inventory inventory = new Inventory();
        inventory.setDescription("The characteristics of someone or something");
        inventory.setId(1L);
        inventory.setName("Name");
        inventory.setProducts(new ArrayList<>());
        inventory.setQuantity(1);
        when(inventoryRepository.save(Mockito.<Inventory>any())).thenReturn(inventory);
        assertEquals(1L,
                inventoryService
                        .createInventoryItem(
                                new InventoryInputDto(1L, "Name", "The characteristics of someone or something", null))
                        .longValue());
        verify(inventoryRepository).save(Mockito.<Inventory>any());
    }


    @Test
    void testCreateInventoryItem7() {
        when(inventoryRepository.save(Mockito.<Inventory>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class, () -> inventoryService
                .createInventoryItem(new InventoryInputDto(1L, "Name", "The characteristics of someone or something", 1)));
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
    void testReadOneInventoryItemId2() {
        Optional<Inventory> emptyResult = Optional.empty();
        when(inventoryRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(ResourceNotFoundException.class, () -> inventoryService.readOneInventoryItemId(1L));
        verify(inventoryRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testReadOneInventoryItemId3() {
        when(inventoryRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class, () -> inventoryService.readOneInventoryItemId(1L));
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
    void testReadOneInventoryItemName2() {
        Optional<Inventory> emptyResult = Optional.empty();
        when(inventoryRepository.findByNameContainingIgnoreCase(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(ResourceNotFoundException.class,
                () -> inventoryService.readOneInventoryItemName("Inventory Item Name"));
        verify(inventoryRepository).findByNameContainingIgnoreCase(Mockito.<String>any());
    }


    @Test
    void testReadOneInventoryItemName3() {
        when(inventoryRepository.findByNameContainingIgnoreCase(Mockito.<String>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class,
                () -> inventoryService.readOneInventoryItemName("Inventory Item Name"));
        verify(inventoryRepository).findByNameContainingIgnoreCase(Mockito.<String>any());
    }


    @Test
    void testReadAllInventoryItems() {
        when(inventoryRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ResourceNotFoundException.class, () -> inventoryService.readAllInventoryItems());
        verify(inventoryRepository).findAll();
    }


    @Test
    void testReadAllInventoryItems2() {
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
    void testReadAllInventoryItems3() {
        when(inventoryRepository.findAll()).thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class, () -> inventoryService.readAllInventoryItems());
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
    void testUpdateOneInventoryItem2() {
        Inventory inventory = new Inventory();
        inventory.setDescription("The characteristics of someone or something");
        inventory.setId(1L);
        inventory.setName("Name");
        inventory.setProducts(new ArrayList<>());
        inventory.setQuantity(1);
        Optional<Inventory> ofResult = Optional.of(inventory);
        when(inventoryRepository.save(Mockito.<Inventory>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        when(inventoryRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(ResourceNotFoundException.class, () -> inventoryService.updateOneInventoryItem(
                new InventoryInputDto(1L, "Name", "The characteristics of someone or something", 1), 1L));
        verify(inventoryRepository).save(Mockito.<Inventory>any());
        verify(inventoryRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testUpdateOneInventoryItem4() {
        Optional<Inventory> emptyResult = Optional.empty();
        when(inventoryRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(ResourceNotFoundException.class, () -> inventoryService.updateOneInventoryItem(
                new InventoryInputDto(1L, "Name", "The characteristics of someone or something", 1), 1L));
        verify(inventoryRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testUpdateOneInventoryItem5() {
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
                new InventoryInputDto(null, "Name", "The characteristics of someone or something", 1), 1L);
        assertEquals("The characteristics of someone or something", actualUpdateOneInventoryItemResult.description);
        assertEquals(1, actualUpdateOneInventoryItemResult.quantity.intValue());
        assertEquals(products, actualUpdateOneInventoryItemResult.products);
        assertEquals("Name", actualUpdateOneInventoryItemResult.name);
        assertEquals(1L, actualUpdateOneInventoryItemResult.id.longValue());
        verify(inventoryRepository).save(Mockito.<Inventory>any());
        verify(inventoryRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testUpdateOneInventoryItem6() {
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
                new InventoryInputDto(1L, null, "The characteristics of someone or something", 1), 1L);
        assertEquals("The characteristics of someone or something", actualUpdateOneInventoryItemResult.description);
        assertEquals(1, actualUpdateOneInventoryItemResult.quantity.intValue());
        assertEquals(products, actualUpdateOneInventoryItemResult.products);
        assertEquals("Name", actualUpdateOneInventoryItemResult.name);
        assertEquals(1L, actualUpdateOneInventoryItemResult.id.longValue());
        verify(inventoryRepository).save(Mockito.<Inventory>any());
        verify(inventoryRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testUpdateOneInventoryItem7() {
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
        InventoryOutputDto actualUpdateOneInventoryItemResult = inventoryService
                .updateOneInventoryItem(new InventoryInputDto(1L, "Name", null, 1), 1L);
        assertEquals("The characteristics of someone or something", actualUpdateOneInventoryItemResult.description);
        assertEquals(1, actualUpdateOneInventoryItemResult.quantity.intValue());
        assertEquals(products, actualUpdateOneInventoryItemResult.products);
        assertEquals("Name", actualUpdateOneInventoryItemResult.name);
        assertEquals(1L, actualUpdateOneInventoryItemResult.id.longValue());
        verify(inventoryRepository).save(Mockito.<Inventory>any());
        verify(inventoryRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testUpdateOneInventoryItem8() {
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
                new InventoryInputDto(1L, "Name", "The characteristics of someone or something", null), 1L);
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


    @Test
    void testDeleteOneInventoryItemId2() {
        Inventory inventory = new Inventory();
        inventory.setDescription("The characteristics of someone or something");
        inventory.setId(1L);
        inventory.setName("Name");
        inventory.setProducts(new ArrayList<>());
        inventory.setQuantity(1);
        Optional<Inventory> ofResult = Optional.of(inventory);
        doThrow(new ResourceNotFoundException("An error occurred")).when(inventoryRepository)
                .delete(Mockito.<Inventory>any());
        when(inventoryRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(ResourceNotFoundException.class, () -> inventoryService.deleteOneInventoryItemId(1L));
        verify(inventoryRepository).findById(Mockito.<Long>any());
        verify(inventoryRepository).delete(Mockito.<Inventory>any());
    }


    @Test
    void testDeleteOneInventoryItemId3() {
        Optional<Inventory> emptyResult = Optional.empty();
        when(inventoryRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(ResourceNotFoundException.class, () -> inventoryService.deleteOneInventoryItemId(1L));
        verify(inventoryRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testInputDtoToEntity() {
        Inventory actualInputDtoToEntityResult = inventoryService
                .inputDtoToEntity(new InventoryInputDto(1L, "Name", "The characteristics of someone or something", 1));
        assertEquals("The characteristics of someone or something", actualInputDtoToEntityResult.getDescription());
        assertEquals(1, actualInputDtoToEntityResult.getQuantity().intValue());
        assertEquals("Name", actualInputDtoToEntityResult.getName());
        assertEquals(1L, actualInputDtoToEntityResult.getId().longValue());
    }


    @Test
    void testInputDtoToEntity2() {
        Inventory actualInputDtoToEntityResult = inventoryService
                .inputDtoToEntity(new InventoryInputDto(null, "Name", "The characteristics of someone or something", 1));
        assertEquals("The characteristics of someone or something", actualInputDtoToEntityResult.getDescription());
        assertEquals(1, actualInputDtoToEntityResult.getQuantity().intValue());
        assertEquals("Name", actualInputDtoToEntityResult.getName());
    }


    @Test
    void testInputDtoToEntity3() {
        Inventory actualInputDtoToEntityResult = inventoryService
                .inputDtoToEntity(new InventoryInputDto(1L, null, "The characteristics of someone or something", 1));
        assertEquals("The characteristics of someone or something", actualInputDtoToEntityResult.getDescription());
        assertEquals(1, actualInputDtoToEntityResult.getQuantity().intValue());
        assertEquals(1L, actualInputDtoToEntityResult.getId().longValue());
    }


    @Test
    void testUpdateInputDtoToEntity() {
        InventoryInputDto inventoryInputDto = new InventoryInputDto(1L, "Name",
                "The characteristics of someone or something", 1);

        Inventory inventoryEntity = new Inventory();
        inventoryEntity.setDescription("The characteristics of someone or something");
        inventoryEntity.setId(1L);
        inventoryEntity.setName("Name");
        inventoryEntity.setProducts(new ArrayList<>());
        inventoryEntity.setQuantity(1);
        Inventory actualUpdateInputDtoToEntityResult = inventoryService.updateInputDtoToEntity(inventoryInputDto,
                inventoryEntity);
        assertSame(inventoryEntity, actualUpdateInputDtoToEntityResult);
        assertEquals("The characteristics of someone or something", actualUpdateInputDtoToEntityResult.getDescription());
        assertEquals(1, actualUpdateInputDtoToEntityResult.getQuantity().intValue());
        assertEquals("Name", actualUpdateInputDtoToEntityResult.getName());
        assertEquals(1L, actualUpdateInputDtoToEntityResult.getId().longValue());
    }


    @Test
    void testUpdateInputDtoToEntity2() {
        InventoryInputDto inventoryInputDto = new InventoryInputDto(null, "Name",
                "The characteristics of someone or something", 1);

        Inventory inventoryEntity = new Inventory();
        inventoryEntity.setDescription("The characteristics of someone or something");
        inventoryEntity.setId(1L);
        inventoryEntity.setName("Name");
        inventoryEntity.setProducts(new ArrayList<>());
        inventoryEntity.setQuantity(1);
        Inventory actualUpdateInputDtoToEntityResult = inventoryService.updateInputDtoToEntity(inventoryInputDto,
                inventoryEntity);
        assertSame(inventoryEntity, actualUpdateInputDtoToEntityResult);
        assertEquals("The characteristics of someone or something", actualUpdateInputDtoToEntityResult.getDescription());
        assertEquals(1, actualUpdateInputDtoToEntityResult.getQuantity().intValue());
        assertEquals("Name", actualUpdateInputDtoToEntityResult.getName());
    }


    @Test
    void testUpdateInputDtoToEntity3() {
        InventoryInputDto inventoryInputDto = new InventoryInputDto(1L, null,
                "The characteristics of someone or something", 1);

        Inventory inventoryEntity = new Inventory();
        inventoryEntity.setDescription("The characteristics of someone or something");
        inventoryEntity.setId(1L);
        inventoryEntity.setName("Name");
        inventoryEntity.setProducts(new ArrayList<>());
        inventoryEntity.setQuantity(1);
        Inventory actualUpdateInputDtoToEntityResult = inventoryService.updateInputDtoToEntity(inventoryInputDto,
                inventoryEntity);
        assertSame(inventoryEntity, actualUpdateInputDtoToEntityResult);
        assertEquals("The characteristics of someone or something", actualUpdateInputDtoToEntityResult.getDescription());
        assertEquals(1, actualUpdateInputDtoToEntityResult.getQuantity().intValue());
        assertEquals(1L, actualUpdateInputDtoToEntityResult.getId().longValue());
    }


    @Test
    void testUpdateInputDtoToEntity4() {
        InventoryInputDto inventoryInputDto = new InventoryInputDto(1L, "Name", null, 1);

        Inventory inventoryEntity = new Inventory();
        inventoryEntity.setDescription("The characteristics of someone or something");
        inventoryEntity.setId(1L);
        inventoryEntity.setName("Name");
        inventoryEntity.setProducts(new ArrayList<>());
        inventoryEntity.setQuantity(1);
        Inventory actualUpdateInputDtoToEntityResult = inventoryService.updateInputDtoToEntity(inventoryInputDto,
                inventoryEntity);
        assertSame(inventoryEntity, actualUpdateInputDtoToEntityResult);
        assertEquals(1, actualUpdateInputDtoToEntityResult.getQuantity().intValue());
        assertEquals("Name", actualUpdateInputDtoToEntityResult.getName());
        assertEquals(1L, actualUpdateInputDtoToEntityResult.getId().longValue());
    }


    @Test
    void testUpdateInputDtoToEntity5() {
        InventoryInputDto inventoryInputDto = new InventoryInputDto(1L, "Name",
                "The characteristics of someone or something", null);

        Inventory inventoryEntity = new Inventory();
        inventoryEntity.setDescription("The characteristics of someone or something");
        inventoryEntity.setId(1L);
        inventoryEntity.setName("Name");
        inventoryEntity.setProducts(new ArrayList<>());
        inventoryEntity.setQuantity(1);
        Inventory actualUpdateInputDtoToEntityResult = inventoryService.updateInputDtoToEntity(inventoryInputDto,
                inventoryEntity);
        assertSame(inventoryEntity, actualUpdateInputDtoToEntityResult);
        assertEquals("The characteristics of someone or something", actualUpdateInputDtoToEntityResult.getDescription());
        assertEquals("Name", actualUpdateInputDtoToEntityResult.getName());
        assertEquals(1L, actualUpdateInputDtoToEntityResult.getId().longValue());
    }


    @Test
    void testEntityToOutputDto() {
        Inventory inventoryItem = new Inventory();
        inventoryItem.setDescription("The characteristics of someone or something");
        inventoryItem.setId(1L);
        inventoryItem.setName("Name");
        inventoryItem.setProducts(new ArrayList<>());
        inventoryItem.setQuantity(1);
        InventoryOutputDto actualEntityToOutputDtoResult = inventoryService.entityToOutputDto(inventoryItem);
        assertEquals("The characteristics of someone or something", actualEntityToOutputDtoResult.description);
        assertEquals(1, actualEntityToOutputDtoResult.quantity.intValue());
        assertTrue(actualEntityToOutputDtoResult.products.isEmpty());
        assertEquals("Name", actualEntityToOutputDtoResult.name);
        assertEquals(1L, actualEntityToOutputDtoResult.id.longValue());
    }


    @Test
    void testEntityToOutputDto2() {
        Inventory inventoryItem = mock(Inventory.class);
        when(inventoryItem.getQuantity()).thenReturn(1);
        when(inventoryItem.getId()).thenReturn(1L);
        when(inventoryItem.getDescription()).thenReturn("The characteristics of someone or something");
        when(inventoryItem.getName()).thenReturn("Name");
        when(inventoryItem.getProducts()).thenReturn(new ArrayList<>());
        doNothing().when(inventoryItem).setDescription(Mockito.<String>any());
        doNothing().when(inventoryItem).setId(Mockito.<Long>any());
        doNothing().when(inventoryItem).setName(Mockito.<String>any());
        doNothing().when(inventoryItem).setProducts(Mockito.<List<Product>>any());
        doNothing().when(inventoryItem).setQuantity(Mockito.<Integer>any());
        inventoryItem.setDescription("The characteristics of someone or something");
        inventoryItem.setId(1L);
        inventoryItem.setName("Name");
        ArrayList<Product> products = new ArrayList<>();
        inventoryItem.setProducts(products);
        inventoryItem.setQuantity(1);
        InventoryOutputDto actualEntityToOutputDtoResult = inventoryService.entityToOutputDto(inventoryItem);
        assertEquals("The characteristics of someone or something", actualEntityToOutputDtoResult.description);
        assertEquals(1, actualEntityToOutputDtoResult.quantity.intValue());
        assertEquals(products, actualEntityToOutputDtoResult.products);
        assertEquals("Name", actualEntityToOutputDtoResult.name);
        assertEquals(1L, actualEntityToOutputDtoResult.id.longValue());
        verify(inventoryItem).getQuantity();
        verify(inventoryItem).getId();
        verify(inventoryItem).getDescription();
        verify(inventoryItem).getName();
        verify(inventoryItem).getProducts();
        verify(inventoryItem).setDescription(Mockito.<String>any());
        verify(inventoryItem).setId(Mockito.<Long>any());
        verify(inventoryItem).setName(Mockito.<String>any());
        verify(inventoryItem).setProducts(Mockito.<List<Product>>any());
        verify(inventoryItem).setQuantity(Mockito.<Integer>any());
    }
}

