package com.example.sparkle.services;

import com.example.sparkle.dtos.CustomerCardInputDto;
import com.example.sparkle.dtos.CustomerCardOutputDto;
import com.example.sparkle.exceptions.ResourceNotFoundException;
import com.example.sparkle.models.CustomerCard;
import com.example.sparkle.repositories.CustomerCardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerCardService {
//    Instance Variables
private final CustomerCardRepository customerCardRepository;


//    Constructor
    public CustomerCardService(CustomerCardRepository customerCardRepository) {
        this.customerCardRepository = customerCardRepository;
    }


//    CRUD:
//    ----------------------------------------------------------------------
//    Create
//    ----------------------------------------------------------------------
    public CustomerCardInputDto createCustomerCard(CustomerCardInputDto cardInputDto){
        customerCardRepository.save(dtoToEntity(cardInputDto));
        return cardInputDto;
    }

//    ----------------------------------------------------------------------
//    Read
//    ----------------------------------------------------------------------
    public List<CustomerCardOutputDto> readAllCustomerCards(){
        List<CustomerCard> customerCardList = customerCardRepository.findAll();
        List<CustomerCardOutputDto> customerCardOutputDtoList = new ArrayList<>();
        for ( CustomerCard savedCardEntity : customerCardList){
            customerCardOutputDtoList.add(entityToDto(savedCardEntity));
        }
        return customerCardOutputDtoList;
    }

    public CustomerCardOutputDto readOneCustomerCard(Long id){
        CustomerCard savedCardEntity = customerCardRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Customercard not found"));
        return entityToDto(savedCardEntity);
    }

//    ----------------------------------------------------------------------
//    Update
//    ----------------------------------------------------------------------

//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------


//    CONVERSIONS:
//    ----------------------------------------------------------------------
//    Entity to Dto
//    ----------------------------------------------------------------------
    public CustomerCardOutputDto entityToDto(CustomerCard cardEntity){
        CustomerCardOutputDto cardOutputDto = new CustomerCardOutputDto();
        cardOutputDto.id = cardEntity.getId();
        cardOutputDto.cardNumber = cardEntity.getCardNumber();
        cardOutputDto.cardStatus = cardEntity.getCardStatus();
        return cardOutputDto;
    }
//    ----------------------------------------------------------------------
//    Dto to Entity
//    ----------------------------------------------------------------------
    public CustomerCard dtoToEntity(CustomerCardInputDto cardInputDto){
        CustomerCard cardEntity = new CustomerCard();
        cardEntity.setId(cardInputDto.id);
        cardEntity.setCardNumber(cardInputDto.cardNumber);
        cardEntity.setCardStatus(cardInputDto.cardStatus);
        cardEntity.setAmountSpend(cardInputDto.amountSpend);
        return cardEntity;
    }
}
