package com.example.sparkle.controllers;

import com.example.sparkle.dtos.CustomerCardInputDto;
import com.example.sparkle.dtos.CustomerCardOutputDto;
import com.example.sparkle.models.CustomerCard;
import com.example.sparkle.services.CustomerCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customercards")
public class CustomerCardController {
//    Instance Variables
    private final CustomerCardService cardService;


//    Constructor
    public CustomerCardController(CustomerCardService customerCardService){
        this.cardService = customerCardService;
    }


//    MAPPINGS:
//    ----------------------------------------------------------------------
//    Post
//    ----------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<CustomerCardInputDto> createCustomerCard(@RequestBody CustomerCardInputDto cardInputDto){
        CustomerCardInputDto newCardDto = cardService.createCustomerCard(cardInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newCardDto ).toUriString());
        return ResponseEntity.created(uri).body(newCardDto);
    }

//    ----------------------------------------------------------------------
//    Get
//    ----------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<CustomerCardOutputDto>> readAllCustomerCards(){
        List<CustomerCardOutputDto> cardDtoList = cardService.readAllCustomerCards();
        return ResponseEntity.ok().body(cardDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerCardOutputDto> readOneCustomerCard(@PathVariable Long id){
        CustomerCardOutputDto cardOutputDto = cardService.readOneCustomerCard(id);
        return ResponseEntity.ok().body(cardOutputDto);
    }

//    ----------------------------------------------------------------------
//    Put
//    ----------------------------------------------------------------------


//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------


}
