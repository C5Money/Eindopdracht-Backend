package com.eindopdracht.sparkle.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.eindopdracht.sparkle.dtos.inputDto.UserInputDto;
import com.eindopdracht.sparkle.dtos.outputDto.UserOutputDto;
import com.eindopdracht.sparkle.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;


    @Test
    void testCreateUser() throws Exception {
        when(userService.readUsers()).thenReturn(new ArrayList<>());

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setApikey("Apikey");
        userInputDto.setAuthorities(new HashSet<>());
        userInputDto.setEmail("jane.doe@example.org");
        userInputDto.setEnabled(true);
        userInputDto.setPassword("iloveyou");
        userInputDto.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testGetUser() throws Exception {
        when(userService.readUser(Mockito.<String>any())).thenReturn(new UserOutputDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{username}", "janedoe");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"username\":null,\"password\":null,\"apikey\":null,\"email\":null,\"authorities\":null,\"customerCard\":null,"
                                        + "\"workSchedule\":null}"));
    }


    @Test
    void testGetUsers() throws Exception {
        when(userService.readUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testAssignCustomerCardToUser() throws Exception {
        when(userService.assignCustomerCardToUser(Mockito.<String>any(), Mockito.<Long>any()))
                .thenReturn("Assign Customer Card To User");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/users/{username}/customercards/{cardNumber}", "janedoe", 1L);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Assign Customer Card To User"));
    }


    @Test
    void testUpdateUser() throws Exception {
        when(userService.updateUser(Mockito.<String>any(), Mockito.<UserInputDto>any())).thenReturn(new UserOutputDto());

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setApikey("Apikey");
        userInputDto.setAuthorities(new HashSet<>());
        userInputDto.setEmail("jane.doe@example.org");
        userInputDto.setEnabled(true);
        userInputDto.setPassword("iloveyou");
        userInputDto.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userInputDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/{username}", "janedoe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(Mockito.<String>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/{username}", "janedoe");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    void testAddUserAuthority() throws Exception {
        doNothing().when(userService).addAuthority(Mockito.<String>any(), Mockito.<String>any());

        HashMap<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put((String) "authority", "foo");
        String content = (new ObjectMapper()).writeValueAsString(stringObjectMap);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{username}/authorities", "janedoe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    void testGetUserAuthorities() throws Exception {
        when(userService.getAuthorities(Mockito.<String>any())).thenReturn(new HashSet<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{username}/authorities",
                "janedoe");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testDeleteUserAuthority() throws Exception {
        doNothing().when(userService).removeAuthority(Mockito.<String>any(), Mockito.<String>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/users/{username}/authorities/{authority}", "janedoe", "JaneDoe");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

