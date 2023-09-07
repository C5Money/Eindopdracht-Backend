package com.eindopdracht.sparkle.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eindopdracht.sparkle.dtos.inputDto.CustomerCardInputDto;
import com.eindopdracht.sparkle.dtos.outputDto.CustomerCardOutputDto;
import com.eindopdracht.sparkle.exceptions.ResourceNotFoundException;
import com.eindopdracht.sparkle.models.CardStatus;
import com.eindopdracht.sparkle.models.CustomerCard;
import com.eindopdracht.sparkle.models.Product;
import com.eindopdracht.sparkle.models.User;
import com.eindopdracht.sparkle.repositories.CustomerCardRepository;

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

@ContextConfiguration(classes = {CustomerCardService.class})
@ExtendWith(SpringExtension.class)
class CustomerCardServiceTest {
    @MockBean
    private CustomerCardRepository customerCardRepository;

    @Autowired
    private CustomerCardService customerCardService;


    @Test
    void testCreateCustomerCard() {
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
        customerCard.setProducts(new ArrayList<>());
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
        when(customerCardRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(ResourceNotFoundException.class,
                () -> customerCardService.createCustomerCard(new CustomerCardInputDto(1L, 10.0d, CardStatus.BRONZE)));
        verify(customerCardRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testDeleteOneCustomerCardById() {
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
        customerCard.setProducts(new ArrayList<>());
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
        doNothing().when(customerCardRepository).delete(Mockito.<CustomerCard>any());
        when(customerCardRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        customerCardService.deleteOneCustomerCardById(1L);
        verify(customerCardRepository).findById(Mockito.<Long>any());
        verify(customerCardRepository).delete(Mockito.<CustomerCard>any());
    }


    @Test
    void testInputDtoToEntity() {
        CustomerCard actualInputDtoToEntityResult = customerCardService
                .inputDtoToEntity(new CustomerCardInputDto(1L, 10.0d, CardStatus.BRONZE));
        assertEquals(10.0d, actualInputDtoToEntityResult.getAmountSpend().doubleValue());
        assertEquals(1L, actualInputDtoToEntityResult.getCardNumber().longValue());
    }


    @Test
    void testEntityToOutputDto() {
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
        CustomerCardOutputDto actualEntityToOutputDtoResult = customerCardService.entityToOutputDto(customerCard2);
        assertEquals(10.0d, actualEntityToOutputDtoResult.amountSpend.doubleValue());
        assertEquals(products, actualEntityToOutputDtoResult.products);
        assertEquals(CardStatus.BRONZE, actualEntityToOutputDtoResult.cardStatus);
        assertEquals(1L, actualEntityToOutputDtoResult.cardNumber.longValue());
    }

    @Test
    void testAutomateSetCardStatus() {
        assertEquals(CardStatus.BRONZE, customerCardService.automateSetCardStatus(10.0d));
        assertEquals(CardStatus.SILVER, customerCardService.automateSetCardStatus(2500.0d));
        assertEquals(CardStatus.GOLD, customerCardService.automateSetCardStatus(6000.0d));
        assertEquals(CardStatus.PLATINUM, customerCardService.automateSetCardStatus(10000.0d));
        assertEquals(CardStatus.PLATINUM, customerCardService.automateSetCardStatus(Double.NaN));
    }
}

