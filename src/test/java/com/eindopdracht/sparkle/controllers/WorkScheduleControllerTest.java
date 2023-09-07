package com.eindopdracht.sparkle.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eindopdracht.sparkle.dtos.inputDto.WorkScheduleInputDto;
import com.eindopdracht.sparkle.dtos.outputDto.WorkScheduleOutputDto;
import com.eindopdracht.sparkle.models.CardStatus;
import com.eindopdracht.sparkle.models.CustomerCard;
import com.eindopdracht.sparkle.models.User;
import com.eindopdracht.sparkle.models.WorkSchedule;
import com.eindopdracht.sparkle.repositories.UserRepository;
import com.eindopdracht.sparkle.repositories.WorkScheduleRepository;
import com.eindopdracht.sparkle.services.WorkScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
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
import org.springframework.validation.BindException;


@ContextConfiguration(classes = {WorkScheduleController.class})
@ExtendWith(SpringExtension.class)
class WorkScheduleControllerTest {
    @Autowired
    private WorkScheduleController workScheduleController;

    @MockBean
    private WorkScheduleService workScheduleService;

    @Test
    void testCreateWorkSchedule() throws Exception {
        when(workScheduleService.readAllWorkSchedules()).thenReturn(new ArrayList<>());
        LocalDate startDate = LocalDate.of(1970, 1, 1);
        WorkScheduleInputDto workScheduleInputDto = new WorkScheduleInputDto(1L, startDate, LocalDate.of(1970, 1, 1), 1);

        workScheduleInputDto.hoursPerWeek = null;
        workScheduleInputDto.endDate = null;
        workScheduleInputDto.startDate = null;
        String content = (new ObjectMapper()).writeValueAsString(workScheduleInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/workschedules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(workScheduleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testReadOneWorkScheduleById() throws Exception {
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

        User user2 = new User();
        user2.setApikey("Apikey");
        user2.setAuthorities(new HashSet<>());
        user2.setCustomerCard(customerCard2);
        user2.setEmail("jane.doe@example.org");
        user2.setEnabled(true);
        user2.setPassword("iloveyou");
        user2.setUsername("janedoe");
        user2.setWorkSchedule(new ArrayList<>());
        WorkScheduleOutputDto workScheduleOutputDto = new WorkScheduleOutputDto();
        workScheduleOutputDto.endDate = LocalDate.of(1970, 1, 1);
        workScheduleOutputDto.hoursPerWeek = 1;
        workScheduleOutputDto.id = 1L;
        workScheduleOutputDto.startDate = LocalDate.of(1970, 1, 1);
        workScheduleOutputDto.user = user2;
        when(workScheduleService.readOneWorkScheduleId(Mockito.<Long>any())).thenReturn(workScheduleOutputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/workschedules/{id}", 1L);
        MockMvcBuilders.standaloneSetup(workScheduleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"startDate\":[1970,1,1],\"endDate\":[1970,1,1],\"hoursPerWeek\":1,\"user\":{\"username\":\"janedoe\","
                                        + "\"password\":\"iloveyou\",\"enabled\":true,\"apikey\":\"Apikey\",\"email\":\"jane.doe@example.org\",\"authorities\":"
                                        + "[],\"customerCard\":{\"cardNumber\":1,\"amountSpend\":10.0,\"cardStatus\":\"BRONZE\"}}}"));
    }


    @Test
    void testReadAllWorkSchedule() throws Exception {
        when(workScheduleService.readAllWorkSchedules()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/workschedules");
        MockMvcBuilders.standaloneSetup(workScheduleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testReadAllWorkSchedulesByDate() throws Exception {
        when(workScheduleService.readAllWorkSchedulesByDate(Mockito.<LocalDate>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/workschedules/date/{date}",
                LocalDate.of(1970, 1, 1));
        MockMvcBuilders.standaloneSetup(workScheduleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testUpdateOneWorkSchedule() {
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

        WorkSchedule workSchedule = new WorkSchedule();
        workSchedule.setEndDate(LocalDate.of(1970, 1, 1));
        workSchedule.setHoursPerWeek(1);
        workSchedule.setId(1L);
        workSchedule.setStartDate(LocalDate.of(1970, 1, 1));
        workSchedule.setUser(user2);
        Optional<WorkSchedule> ofResult = Optional.of(workSchedule);

        CustomerCard customerCard2 = new CustomerCard();
        customerCard2.setAmountSpend(10.0d);
        customerCard2.setCardNumber(1L);
        customerCard2.setCardStatus(CardStatus.BRONZE);
        customerCard2.setProducts(new ArrayList<>());
        customerCard2.setUser(new User());

        User user3 = new User();
        user3.setApikey("Apikey");
        user3.setAuthorities(new HashSet<>());
        user3.setCustomerCard(customerCard2);
        user3.setEmail("jane.doe@example.org");
        user3.setEnabled(true);
        user3.setPassword("iloveyou");
        user3.setUsername("janedoe");
        user3.setWorkSchedule(new ArrayList<>());

        CustomerCard customerCard3 = new CustomerCard();
        customerCard3.setAmountSpend(10.0d);
        customerCard3.setCardNumber(1L);
        customerCard3.setCardStatus(CardStatus.BRONZE);
        customerCard3.setProducts(new ArrayList<>());
        customerCard3.setUser(user3);

        User user4 = new User();
        user4.setApikey("Apikey");
        user4.setAuthorities(new HashSet<>());
        user4.setCustomerCard(customerCard3);
        user4.setEmail("jane.doe@example.org");
        user4.setEnabled(true);
        user4.setPassword("iloveyou");
        user4.setUsername("janedoe");
        user4.setWorkSchedule(new ArrayList<>());

        WorkSchedule workSchedule2 = new WorkSchedule();
        workSchedule2.setEndDate(LocalDate.of(1970, 1, 1));
        workSchedule2.setHoursPerWeek(1);
        workSchedule2.setId(1L);
        workSchedule2.setStartDate(LocalDate.of(1970, 1, 1));
        workSchedule2.setUser(user4);
        WorkScheduleRepository workScheduleRepository = mock(WorkScheduleRepository.class);
        when(workScheduleRepository.save(Mockito.<WorkSchedule>any())).thenReturn(workSchedule2);
        when(workScheduleRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        WorkScheduleController workScheduleController = new WorkScheduleController(
                new WorkScheduleService(workScheduleRepository, mock(UserRepository.class)));
        LocalDate startDate = LocalDate.of(1970, 1, 1);
        WorkScheduleInputDto workScheduleInputDto = new WorkScheduleInputDto(1L, startDate, LocalDate.of(1970, 1, 1), 1);

        ResponseEntity<Object> actualUpdateOneWorkScheduleResult = workScheduleController
                .updateOneWorkSchedule(workScheduleInputDto, 1L, new BindException("Target", "Object Name"));
        assertTrue(actualUpdateOneWorkScheduleResult.hasBody());
        assertEquals(202, actualUpdateOneWorkScheduleResult.getStatusCodeValue());
        assertTrue(actualUpdateOneWorkScheduleResult.getHeaders().isEmpty());
        assertEquals("1970-01-01",
                ((WorkScheduleOutputDto) actualUpdateOneWorkScheduleResult.getBody()).startDate.toString());
        assertEquals(1, ((WorkScheduleOutputDto) actualUpdateOneWorkScheduleResult.getBody()).hoursPerWeek.intValue());
        assertEquals("1970-01-01",
                ((WorkScheduleOutputDto) actualUpdateOneWorkScheduleResult.getBody()).endDate.toString());
        assertEquals(1L, ((WorkScheduleOutputDto) actualUpdateOneWorkScheduleResult.getBody()).id.longValue());
        assertSame(user2, ((WorkScheduleOutputDto) actualUpdateOneWorkScheduleResult.getBody()).user);
        verify(workScheduleRepository).save(Mockito.<WorkSchedule>any());
        verify(workScheduleRepository).findById(Mockito.<Long>any());
    }


    @Test
    void testAssignUserToWorkSchedules() throws Exception {
        when(workScheduleService.assignUserToWorkSchedules(Mockito.<Long>any(), Mockito.<String>any()))
                .thenReturn("Assign User To Work Schedules");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/workschedules/{id}/users/{userId}", 1L,
                "42");
        MockMvcBuilders.standaloneSetup(workScheduleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Assign User To Work Schedules"));
    }


    @Test
    void testDeleteOneWorkScheduleById() throws Exception {
        doNothing().when(workScheduleService).deleteOneWorkScheduleId(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/workschedules/{id}", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(workScheduleController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

