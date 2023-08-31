package com.eindopdracht.sparkle.controllers;

import com.eindopdracht.sparkle.dtos.inputDto.CustomerCardInputDto;
import com.eindopdracht.sparkle.dtos.outputDto.CustomerCardOutputDto;
import com.eindopdracht.sparkle.models.CardStatus;
import com.eindopdracht.sparkle.services.CustomerCardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    public ResponseEntity<Object> createCustomerCard(@Valid @RequestBody CustomerCardInputDto cardInputDto, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                stringBuilder.append(fieldError.getField() + ": ");
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        }

        Long newCardDto = cardService.createCustomerCard(cardInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newCardDto ).toUriString());
        cardInputDto.cardNumber = newCardDto;
        return ResponseEntity.created(uri).body("Customercard with id: " + cardInputDto.cardNumber + " is succesfully created.");
    }
//    ----------------------------------------------------------------------
//    Get
//    ----------------------------------------------------------------------
    @GetMapping("/{cardNumber}")
    public ResponseEntity<CustomerCardOutputDto> readOneCustomerCardByCardNumber(@PathVariable Long cardNumber){
        CustomerCardOutputDto cardOutputDto = cardService.readOneCustomerCardByCardNumber(cardNumber);
        return ResponseEntity.ok().body(cardOutputDto);
    }

    @GetMapping("/status/{cardStatus}")
    public ResponseEntity<List<CustomerCardOutputDto>> readAllCustomerCardsByCardStatus(@PathVariable CardStatus cardStatus){
        List<CustomerCardOutputDto> cardDtoList = cardService.readAllCustomerCardsByCardStatus(cardStatus);
        return ResponseEntity.ok().body(cardDtoList);
    }

    @GetMapping
    public ResponseEntity<List<CustomerCardOutputDto>> readAllCustomerCards(){
        List<CustomerCardOutputDto> cardDtoList = cardService.readAllCustomerCards();
        return ResponseEntity.ok().body(cardDtoList);
    }
//    ----------------------------------------------------------------------
//    Put
//    ----------------------------------------------------------------------
    @PutMapping("/{cardNumber}")
    public ResponseEntity<Object> updateCustomerCard(@RequestBody CustomerCardInputDto cardInputDto, @PathVariable Long cardNumber){
        CustomerCardOutputDto cardOutputDto = cardService.updateOneCustomerCard(cardInputDto, cardNumber);
        return ResponseEntity.accepted().body(cardOutputDto);
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    @DeleteMapping("/{cardNumber}")
    public ResponseEntity<HttpStatus> deleteOneCustomerCard(@PathVariable Long cardNumber){
        cardService.deleteOneCustomerCardById(cardNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}