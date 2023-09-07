package com.eindopdracht.sparkle.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eindopdracht.sparkle.dtos.inputDto.CustomerCardInputDto;
import com.eindopdracht.sparkle.dtos.outputDto.CustomerCardOutputDto;
import com.eindopdracht.sparkle.models.CardStatus;
import com.eindopdracht.sparkle.models.CustomerCard;
import com.eindopdracht.sparkle.models.Product;
import com.eindopdracht.sparkle.models.User;
import com.eindopdracht.sparkle.repositories.CustomerCardRepository;
import com.eindopdracht.sparkle.services.CustomerCardService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
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

@ContextConfiguration(classes = {CustomerCardController.class})
@ExtendWith(SpringExtension.class)
class CustomerCardControllerTest {
    @Autowired
    private CustomerCardController customerCardController;

    @MockBean
    private CustomerCardService customerCardService;

    @Test
    void testCreateCustomerCard() throws Exception {
        when(customerCardService.readAllCustomerCards()).thenReturn(new ArrayList<>());
        CustomerCardInputDto customerCardInputDto = new CustomerCardInputDto(1L, 10.0d, CardStatus.BRONZE);

        customerCardInputDto.cardNumber = null;
        customerCardInputDto.amountSpend = null;
        customerCardInputDto.cardStatus = null;
        String content = (new ObjectMapper()).writeValueAsString(customerCardInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customercards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(customerCardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testReadOneCustomerCardByCardNumber() throws Exception {
        CustomerCardOutputDto customerCardOutputDto = new CustomerCardOutputDto();
        customerCardOutputDto.amountSpend = 10.0d;
        customerCardOutputDto.cardNumber = 1L;
        customerCardOutputDto.cardStatus = CardStatus.BRONZE;
        customerCardOutputDto.products = new ArrayList<>();
        when(customerCardService.readOneCustomerCardByCardNumber(Mockito.<Long>any())).thenReturn(customerCardOutputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customercards/{cardNumber}", 1L);
        MockMvcBuilders.standaloneSetup(customerCardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"cardNumber\":1,\"amountSpend\":10.0,\"cardStatus\":\"BRONZE\",\"products\":[]}"));
    }


    @Test
    void testReadAllCustomerCards() throws Exception {
        when(customerCardService.readAllCustomerCards()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customercards");
        MockMvcBuilders.standaloneSetup(customerCardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testUpdateCustomerCard2() {
        User user = new User();
        user.setApikey("Apikey");
        user.setAuthorities(new HashSet<>());
        user.setCustomerCard(new CustomerCard());
        user.setEmail("jane.doe@example.org");
        user.setEnabled(true);
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setWorkSchedule(new ArrayList<>());

        CustomerCard customerCard = new CustomerCard();
        customerCard.setAmountSpend(10.0d);
        customerCard.setCardNumber(1L);
        customerCard.setCardStatus(CardStatus.BRONZE);
        ArrayList<Product> products = new ArrayList<>();
        customerCard.setProducts(products);
        customerCard.setUser(user);

        User user2 = new User();
        user2.setApikey("Apikey");
        user2.setAuthorities(new HashSet<>());
        user2.setCustomerCard(customerCard);
        user2.setEmail("jane.doe@example.org");
        user2.setEnabled(true);
        user2.setPassword("iloveyou");
        user2.setUsername("janedoe");
        user2.setWorkSchedule(new ArrayList<>());

        CustomerCard customerCard2 = new CustomerCard();
        customerCard2.setAmountSpend(10.0d);
        customerCard2.setCardNumber(1L);
        customerCard2.setCardStatus(CardStatus.BRONZE);
        customerCard2.setProducts(new ArrayList<>());
        customerCard2.setUser(user2);
        Optional<CustomerCard> ofResult = Optional.of(customerCard2);

        CustomerCard customerCard3 = new CustomerCard();
        customerCard3.setAmountSpend(10.0d);
        customerCard3.setCardNumber(1L);
        customerCard3.setCardStatus(CardStatus.BRONZE);
        customerCard3.setProducts(new ArrayList<>());
        customerCard3.setUser(new User());

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

        User user4 = new User();
        user4.setApikey("Apikey");
        user4.setAuthorities(new HashSet<>());
        user4.setCustomerCard(customerCard4);
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
        CustomerCardRepository customerCardRepository = mock(CustomerCardRepository.class);
        when(customerCardRepository.save(Mockito.<CustomerCard>any())).thenReturn(customerCard5);
        when(customerCardRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        CustomerCardController customerCardController = new CustomerCardController(
                new CustomerCardService(customerCardRepository));
        CustomerCardInputDto cardInputDto = new CustomerCardInputDto(1L, 10.0d, CardStatus.BRONZE);

        cardInputDto.cardNumber = null;
        cardInputDto.amountSpend = null;
        cardInputDto.cardStatus = null;
        ResponseEntity<Object> actualUpdateCustomerCardResult = customerCardController.updateCustomerCard(cardInputDto,
                1L);
        assertTrue(actualUpdateCustomerCardResult.hasBody());
        assertEquals(202, actualUpdateCustomerCardResult.getStatusCodeValue());
        assertTrue(actualUpdateCustomerCardResult.getHeaders().isEmpty());
        assertEquals(products, ((CustomerCardOutputDto) Objects.requireNonNull(actualUpdateCustomerCardResult.getBody())).products);
        assertEquals(10.0d, ((CustomerCardOutputDto) actualUpdateCustomerCardResult.getBody()).amountSpend.doubleValue());
        assertEquals(CardStatus.BRONZE, ((CustomerCardOutputDto) actualUpdateCustomerCardResult.getBody()).cardStatus);
        assertEquals(1L, ((CustomerCardOutputDto) actualUpdateCustomerCardResult.getBody()).cardNumber.longValue());
        verify(customerCardRepository).save(Mockito.<CustomerCard>any());
        verify(customerCardRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testUpdateCustomerCard5() {
        CustomerCardOutputDto customerCardOutputDto = new CustomerCardOutputDto();
        customerCardOutputDto.amountSpend = 10.0d;
        customerCardOutputDto.cardNumber = 1L;
        customerCardOutputDto.cardStatus = CardStatus.BRONZE;
        customerCardOutputDto.products = new ArrayList<>();
        CustomerCardService customerCardService = mock(CustomerCardService.class);
        when(customerCardService.updateOneCustomerCard(Mockito.<CustomerCardInputDto>any(), Mockito.<Long>any()))
                .thenReturn(customerCardOutputDto);
        CustomerCardController customerCardController = new CustomerCardController(customerCardService);
        CustomerCardInputDto cardInputDto = new CustomerCardInputDto(1L, 10.0d, CardStatus.BRONZE);

        cardInputDto.cardNumber = null;
        cardInputDto.amountSpend = null;
        cardInputDto.cardStatus = CardStatus.BRONZE;
        ResponseEntity<Object> actualUpdateCustomerCardResult = customerCardController.updateCustomerCard(cardInputDto,
                1L);
        assertTrue(actualUpdateCustomerCardResult.hasBody());
        assertEquals(202, actualUpdateCustomerCardResult.getStatusCodeValue());
        assertTrue(actualUpdateCustomerCardResult.getHeaders().isEmpty());
        verify(customerCardService).updateOneCustomerCard(Mockito.<CustomerCardInputDto>any(), Mockito.<Long>any());
    }


    @Test
    void testDeleteOneCustomerCard() throws Exception {
        doNothing().when(customerCardService).deleteOneCustomerCardById(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/customercards/{cardNumber}", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerCardController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteOneCustomerCard2() throws Exception {
        doNothing().when(customerCardService).deleteOneCustomerCardById(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/customercards/{cardNumber}", 1L);
        requestBuilder.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerCardController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

