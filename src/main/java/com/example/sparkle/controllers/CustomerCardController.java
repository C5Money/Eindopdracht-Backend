package com.example.sparkle.controllers;

import com.example.sparkle.dtos.inputDto.CustomerCardInputDto;
import com.example.sparkle.dtos.outputDto.CustomerCardOutputDto;
import com.example.sparkle.services.CustomerCardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

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
        cardInputDto.id = newCardDto;
        return ResponseEntity.created(uri).body(cardInputDto);
    }
//    ----------------------------------------------------------------------
//    Get
//    ----------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<CustomerCardOutputDto> readOneCustomerCard(@PathVariable Long id){
        CustomerCardOutputDto cardOutputDto = cardService.readOneCustomerCard(id);
        return ResponseEntity.ok().body(cardOutputDto);
    }

    @GetMapping
    public ResponseEntity<List<CustomerCardOutputDto>> readAllCustomerCards(){
        List<CustomerCardOutputDto> cardDtoList = cardService.readAllCustomerCards();
        return ResponseEntity.ok().body(cardDtoList);
    }
//    ----------------------------------------------------------------------
//    Put
//    ----------------------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<CustomerCardOutputDto> updateOneCustomerCard(@RequestBody CustomerCardInputDto cardInputDto, @PathVariable Long id){
        CustomerCardOutputDto cardOutputDto = cardService.updateOneCustomerCard(cardInputDto, id);
        return ResponseEntity.accepted().body(cardOutputDto);
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOneCustomerCard(@PathVariable Long id){
        cardService.deleteOneCustomerCard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}