package com.eindopdracht.sparkle.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eindopdracht.sparkle.dtos.inputDto.ProductInputDto;
import com.eindopdracht.sparkle.dtos.outputDto.ProductOutputDto;
import com.eindopdracht.sparkle.models.CardStatus;
import com.eindopdracht.sparkle.models.CustomerCard;
import com.eindopdracht.sparkle.models.Inventory;
import com.eindopdracht.sparkle.models.Product;
import com.eindopdracht.sparkle.models.User;
import com.eindopdracht.sparkle.repositories.CustomerCardRepository;
import com.eindopdracht.sparkle.repositories.InventoryRepository;
import com.eindopdracht.sparkle.repositories.ProductRepository;
import com.eindopdracht.sparkle.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindException;

@SpringBootTest
@ContextConfiguration(classes = {ProductController.class})
@ExtendWith(SpringExtension.class)
class ProductControllerIntegrationTest {

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @Test
    void testCreateProduct() throws Exception {
        when(productService.readAllProducts()).thenReturn(new ArrayList<>());
        ProductInputDto productInputDto = new ProductInputDto("Product Name", 1L, 10.0d, 10.0d, "Category");

        productInputDto.unitPrice = null;
        productInputDto.articleNumber = null;
        productInputDto.availableStock = null;
        productInputDto.category = null;
        productInputDto.productName = null;
        String content = (new ObjectMapper()).writeValueAsString(productInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testReadOneProductByArticleNumber() throws Exception {
        Inventory inventory = new Inventory();
        inventory.setDescription("The characteristics of someone or something");
        inventory.setId(1L);
        inventory.setName("Name");
        inventory.setProducts(new ArrayList<>());
        inventory.setQuantity(1);
        ProductOutputDto productOutputDto = new ProductOutputDto();
        productOutputDto.articleNumber = 1L;
        productOutputDto.availableStock = 10.0d;
        productOutputDto.inventory = inventory;
        productOutputDto.productName = "Product Name";
        productOutputDto.unitPrice = 10.0d;
        when(productService.readOneProductByArticleNumber(Mockito.<Long>any())).thenReturn(productOutputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/{articleNumber}", 1L);
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"productName\":\"Product Name\",\"articleNumber\":1,\"unitPrice\":10.0,\"availableStock\":10.0,\"inventory\":{"
                                        + "\"id\":1,\"name\":\"Name\",\"description\":\"The characteristics of someone or something\",\"quantity\":1}}"));
    }

    @Test
    void testReadOneProductByName() throws Exception {
        Inventory inventory = new Inventory();
        inventory.setDescription("The characteristics of someone or something");
        inventory.setId(1L);
        inventory.setName("Name");
        inventory.setProducts(new ArrayList<>());
        inventory.setQuantity(1);
        ProductOutputDto productOutputDto = new ProductOutputDto();
        productOutputDto.articleNumber = 1L;
        productOutputDto.availableStock = 10.0d;
        productOutputDto.inventory = inventory;
        productOutputDto.productName = "Product Name";
        productOutputDto.unitPrice = 10.0d;
        when(productService.readOneProductName(Mockito.<String>any())).thenReturn(productOutputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/name/{name}", "Name");
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"productName\":\"Product Name\",\"articleNumber\":1,\"unitPrice\":10.0,\"availableStock\":10.0,\"inventory\":{"
                                        + "\"id\":1,\"name\":\"Name\",\"description\":\"The characteristics of someone or something\",\"quantity\":1}}"));
    }

    @Test
    void testReadAllProducts() throws Exception {
        when(productService.readAllProducts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products");
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testUpdateOneProduct() {
        CustomerCard customerCard = new CustomerCard();
        customerCard.setAmountSpend(10.0d);
        customerCard.setCardNumber(1L);
        customerCard.setCardStatus(CardStatus.BRONZE);
        customerCard.setProducts(new ArrayList<>());
        customerCard.setUser(new User());

        User user = new User();
        user.setApikey("Apikey");
        user.setAuthorities(new HashSet<>());
        user.setCustomerCard(customerCard);
        user.setEmail("jane.doe@example.org");
        user.setEnabled(true);
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setWorkSchedule(new ArrayList<>());

        CustomerCard customerCard2 = new CustomerCard();
        customerCard2.setAmountSpend(10.0d);
        customerCard2.setCardNumber(1L);
        customerCard2.setCardStatus(CardStatus.BRONZE);
        customerCard2.setProducts(new ArrayList<>());
        customerCard2.setUser(user);

        Inventory inventoryItem = new Inventory();
        inventoryItem.setDescription("The characteristics of someone or something");
        inventoryItem.setId(1L);
        inventoryItem.setName("Name");
        inventoryItem.setProducts(new ArrayList<>());
        inventoryItem.setQuantity(1);

        Product product = new Product();
        product.setArticleNumber(1L);
        product.setAvailableStock(10.0d);
        product.setCategory("Category");
        product.setCustomerCard(customerCard2);
        product.setInventoryItem(inventoryItem);
        product.setProductName("Product Name");
        product.setUnitPrice(10.0d);
        Optional<Product> ofResult = Optional.of(product);

        User user2 = new User();
        user2.setApikey("Apikey");
        user2.setAuthorities(new HashSet<>());
        user2.setCustomerCard(new CustomerCard());
        user2.setEmail("jane.doe@example.org");
        user2.setEnabled(true);
        user2.setPassword("iloveyou");
        user2.setUsername("janedoe");
        user2.setWorkSchedule(new ArrayList<>());

        CustomerCard customerCard3 = new CustomerCard();
        customerCard3.setAmountSpend(10.0d);
        customerCard3.setCardNumber(1L);
        customerCard3.setCardStatus(CardStatus.BRONZE);
        customerCard3.setProducts(new ArrayList<>());
        customerCard3.setUser(user2);

        User user3 = new User();
        user3.setApikey("Apikey");
        user3.setAuthorities(new HashSet<>());
        user3.setCustomerCard(customerCard3);
        user3.setEmail("jane.doe@example.org");
        user3.setEnabled(true);
        user3.setPassword("iloveyou");
        user3.setUsername("janedoe");
        user3.setWorkSchedule(new ArrayList<>());

        CustomerCard customerCard4 = new CustomerCard();
        customerCard4.setAmountSpend(10.0d);
        customerCard4.setCardNumber(1L);
        customerCard4.setCardStatus(CardStatus.BRONZE);
        customerCard4.setProducts(new ArrayList<>());
        customerCard4.setUser(user3);

        Inventory inventoryItem2 = new Inventory();
        inventoryItem2.setDescription("The characteristics of someone or something");
        inventoryItem2.setId(1L);
        inventoryItem2.setName("Name");
        inventoryItem2.setProducts(new ArrayList<>());
        inventoryItem2.setQuantity(1);

        Product product2 = new Product();
        product2.setArticleNumber(1L);
        product2.setAvailableStock(10.0d);
        product2.setCategory("Category");
        product2.setCustomerCard(customerCard4);
        product2.setInventoryItem(inventoryItem2);
        product2.setProductName("Product Name");
        product2.setUnitPrice(10.0d);
        ProductRepository productRepository = mock(ProductRepository.class);
        when(productRepository.save(Mockito.<Product>any())).thenReturn(product2);
        when(productRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ProductController productController = new ProductController(
                new ProductService(productRepository, mock(CustomerCardRepository.class), mock(InventoryRepository.class)));
        ProductInputDto productInputDto = new ProductInputDto("Product Name", 1L, 10.0d, 10.0d, "Category");

        ResponseEntity<Object> actualUpdateOneProductResult = productController.updateOneProduct(1L, productInputDto,
                new BindException("Target", "Object Name"));
        assertTrue(actualUpdateOneProductResult.hasBody());
        assertEquals(200, actualUpdateOneProductResult.getStatusCodeValue());
        assertTrue(actualUpdateOneProductResult.getHeaders().isEmpty());
        assertEquals("Product Name", ((ProductOutputDto) actualUpdateOneProductResult.getBody()).productName);
        assertEquals(10.0d, ((ProductOutputDto) actualUpdateOneProductResult.getBody()).availableStock.doubleValue());
        assertEquals(1L, ((ProductOutputDto) actualUpdateOneProductResult.getBody()).articleNumber.longValue());
        assertSame(inventoryItem, ((ProductOutputDto) actualUpdateOneProductResult.getBody()).inventory);
        assertEquals(10.0d, ((ProductOutputDto) actualUpdateOneProductResult.getBody()).unitPrice.doubleValue());
        verify(productRepository).save(Mockito.<Product>any());
        verify(productRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testAssignProductToInventoryItem() throws Exception {
        when(productService.assignProductToInventoryItem(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Assign Product To Inventory Item");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{articleNumber}/inventories/{inventoryId}", 1L, 1L);
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Assign Product To Inventory Item"));
    }


    @Test
    void testAssignProductToCustomerCard() throws Exception {
        when(productService.assignProductToCustomerCard(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("Assign Product To Customer Card");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{articleNumber}/cardnumber/{cardnumber}", 1L, 1L);
        MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Assign Product To Customer Card"));
    }


    @Test
    void testDeleteOneProductById() throws Exception {
        doNothing().when(productService).deleteOneProductId(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/products/{articleNumber}", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(productController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}

