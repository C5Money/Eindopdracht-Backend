package com.eindopdracht.sparkle.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eindopdracht.sparkle.dtos.inputDto.ProductInputDto;
import com.eindopdracht.sparkle.dtos.outputDto.ProductOutputDto;
import com.eindopdracht.sparkle.exceptions.ResourceNotFoundException;
import com.eindopdracht.sparkle.models.CardStatus;
import com.eindopdracht.sparkle.models.CustomerCard;
import com.eindopdracht.sparkle.models.Inventory;
import com.eindopdracht.sparkle.models.Product;
import com.eindopdracht.sparkle.models.User;
import com.eindopdracht.sparkle.repositories.CustomerCardRepository;
import com.eindopdracht.sparkle.repositories.InventoryRepository;
import com.eindopdracht.sparkle.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.HashSet;
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

@ContextConfiguration(classes = {ProductService.class})
@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    @MockBean
    private CustomerCardRepository customerCardRepository;

    @MockBean
    private InventoryRepository inventoryRepository;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;


    @Test
    void testCreateProduct() {
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
        when(productRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(ResourceNotFoundException.class,
                () -> productService.createProduct(new ProductInputDto("Product Name", 1L, 10.0d, 10.0d, "Category")));
        verify(productRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testReadOneProductByArticleNumber() {
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
        when(productRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ProductOutputDto actualReadOneProductByArticleNumberResult = productService.readOneProductByArticleNumber(3L);
        assertEquals(1L, actualReadOneProductByArticleNumberResult.articleNumber.longValue());
        assertEquals(10.0d, actualReadOneProductByArticleNumberResult.unitPrice.doubleValue());
        assertEquals("Product Name", actualReadOneProductByArticleNumberResult.productName);
        assertEquals(10.0d, actualReadOneProductByArticleNumberResult.availableStock.doubleValue());
        verify(productRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testReadOneProductName() {
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
        when(productRepository.findByProductNameContainingIgnoreCase(Mockito.<String>any())).thenReturn(ofResult);
        ProductOutputDto actualReadOneProductNameResult = productService.readOneProductName("Productname");
        assertEquals(1L, actualReadOneProductNameResult.articleNumber.longValue());
        assertEquals(10.0d, actualReadOneProductNameResult.unitPrice.doubleValue());
        assertEquals("Product Name", actualReadOneProductNameResult.productName);
        assertEquals(10.0d, actualReadOneProductNameResult.availableStock.doubleValue());
        verify(productRepository).findByProductNameContainingIgnoreCase(Mockito.<String>any());
    }


    @Test
    void testReadAllProducts() {
        CustomerCard customerCard = new CustomerCard();
        customerCard.setAmountSpend(10.0d);
        customerCard.setCardNumber(1L);
        customerCard.setCardStatus(CardStatus.BRONZE);
        customerCard.setProducts(new ArrayList<>());
        customerCard.setUser(new User());

        User user = new User();
        user.setApikey("Products not found.");
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
        inventoryItem.setName("Products not found.");
        inventoryItem.setProducts(new ArrayList<>());
        inventoryItem.setQuantity(1);

        Product product = new Product();
        product.setArticleNumber(1L);
        product.setAvailableStock(10.0d);
        product.setCategory("Products not found.");
        product.setCustomerCard(customerCard2);
        product.setInventoryItem(inventoryItem);
        product.setProductName("Products not found.");
        product.setUnitPrice(10.0d);

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productRepository.findAll()).thenReturn(productList);
        List<ProductOutputDto> actualReadAllProductsResult = productService.readAllProducts();
        assertEquals(1, actualReadAllProductsResult.size());
        ProductOutputDto getResult = actualReadAllProductsResult.get(0);
        assertEquals(1L, getResult.articleNumber.longValue());
        assertEquals(10.0d, getResult.unitPrice.doubleValue());
        assertEquals("Products not found.", getResult.productName);
        assertSame(inventoryItem, getResult.inventory);
        assertEquals(10.0d, getResult.availableStock.doubleValue());
        verify(productRepository).findAll();
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
        when(productRepository.save(Mockito.<Product>any())).thenReturn(product2);
        when(productRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ProductOutputDto actualUpdateOneProductResult = productService.updateOneProduct(1L,
                new ProductInputDto("Product Name", 1L, 10.0d, 10.0d, "Category"));
        assertEquals(1L, actualUpdateOneProductResult.articleNumber.longValue());
        assertEquals(10.0d, actualUpdateOneProductResult.unitPrice.doubleValue());
        assertEquals("Product Name", actualUpdateOneProductResult.productName);
        assertEquals(10.0d, actualUpdateOneProductResult.availableStock.doubleValue());
        verify(productRepository).save(Mockito.<Product>any());
        verify(productRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testAssignProductToInventoryItem() {
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
        when(productRepository.save(Mockito.<Product>any())).thenReturn(product2);
        when(productRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Inventory inventory = new Inventory();
        inventory.setDescription("The characteristics of someone or something");
        inventory.setId(1L);
        inventory.setName("Name");
        inventory.setProducts(new ArrayList<>());
        inventory.setQuantity(1);
        Optional<Inventory> ofResult2 = Optional.of(inventory);
        when(inventoryRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        assertEquals("Product with id: 1 has successfully been assigned to inventory item id: 1.",
                productService.assignProductToInventoryItem(1L, 1L));
        verify(productRepository).save(Mockito.<Product>any());
        verify(productRepository).findById(Mockito.<Long>any());
        verify(inventoryRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testAssignProductToCustomerCard() {
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
        when(productRepository.save(Mockito.<Product>any())).thenReturn(product2);
        when(productRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        User user4 = new User();
        user4.setApikey("Apikey");
        user4.setAuthorities(new HashSet<>());
        user4.setCustomerCard(new CustomerCard());
        user4.setEmail("jane.doe@example.org");
        user4.setEnabled(true);
        user4.setPassword("iloveyou");
        user4.setUsername("janedoe");
        user4.setWorkSchedule(new ArrayList<>());

        CustomerCard customerCard5 = new CustomerCard();
        customerCard5.setAmountSpend(10.0d);
        customerCard5.setCardNumber(1L);
        customerCard5.setCardStatus(CardStatus.BRONZE);
        customerCard5.setProducts(new ArrayList<>());
        customerCard5.setUser(user4);

        User user5 = new User();
        user5.setApikey("Apikey");
        user5.setAuthorities(new HashSet<>());
        user5.setCustomerCard(customerCard5);
        user5.setEmail("jane.doe@example.org");
        user5.setEnabled(true);
        user5.setPassword("iloveyou");
        user5.setUsername("janedoe");
        user5.setWorkSchedule(new ArrayList<>());

        CustomerCard customerCard6 = new CustomerCard();
        customerCard6.setAmountSpend(10.0d);
        customerCard6.setCardNumber(1L);
        customerCard6.setCardStatus(CardStatus.BRONZE);
        customerCard6.setProducts(new ArrayList<>());
        customerCard6.setUser(user5);
        Optional<CustomerCard> ofResult2 = Optional.of(customerCard6);
        when(customerCardRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        assertEquals("Product with id: 1 has successfully been assigned to customercardnumber: 1.",
                productService.assignProductToCustomerCard(1L, 1L));
        verify(productRepository).save(Mockito.<Product>any());
        verify(productRepository).findById(Mockito.<Long>any());
        verify(customerCardRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testDeleteOneProductId() {
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
        doNothing().when(productRepository).delete(Mockito.<Product>any());
        when(productRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        productService.deleteOneProductId(1L);
        verify(productRepository).findById(Mockito.<Long>any());
        verify(productRepository).delete(Mockito.<Product>any());
    }
}

