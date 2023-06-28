package com.example.sparkle.services;

import com.example.sparkle.dtos.CustomerCardInputDto;
import com.example.sparkle.dtos.CustomerCardOutputDto;
import com.example.sparkle.exceptions.ResourceNotFoundException;
import com.example.sparkle.models.CustomerCard;
import com.example.sparkle.repositories.CustomerCardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        customerCardRepository.save(inputDtoToEntity(cardInputDto));
        return cardInputDto;
    }
//    ----------------------------------------------------------------------
//    Read
//    ----------------------------------------------------------------------
    public CustomerCardOutputDto readOneCustomerCard(Long id){
        Optional<CustomerCard> optionalCustomerCard = customerCardRepository.findById(id);
        if(optionalCustomerCard.isEmpty() || id <= 0){
            throw new ResourceNotFoundException("This id: " + id + " is invalid.");
        }
        return entityToOutputDto(optionalCustomerCard.get());
    }

    public List<CustomerCardOutputDto> readAllCustomerCards(){
        List<CustomerCard> customerCardList = customerCardRepository.findAll();
        List<CustomerCardOutputDto> customerCardOutputDtoList = new ArrayList<>();
        for ( CustomerCard customerCardEntity : customerCardList){
            customerCardOutputDtoList.add(entityToOutputDto(customerCardEntity));
        }
        return customerCardOutputDtoList;
    }
//    ----------------------------------------------------------------------
//    Update
//    ----------------------------------------------------------------------
    public CustomerCardOutputDto updateOneCustomerCard(CustomerCardInputDto cardInputDto, Long id){
        Optional<CustomerCard> optionalCustomerCard = customerCardRepository.findById(id);
        if(optionalCustomerCard.isEmpty() || id <= 0){
            throw new ResourceNotFoundException("This id: " + id + " is not found.");
        }
        CustomerCardOutputDto updatableOutputDto = entityToOutputDto(optionalCustomerCard.get());

        customerCardRepository.save(inputDtoToEntity(cardInputDto));
        return updatableOutputDto;
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    public void deleteOneCustomerCard(Long id){
        Optional<CustomerCard> optionalCustomerCard = customerCardRepository.findById(id);
        if (optionalCustomerCard.isEmpty() || id <= 0){
            throw new ResourceNotFoundException("This id: " + id + " is invalid.");
        }
        customerCardRepository.deleteById(id);
    }

//    MAPPERS:
//    ----------------------------------------------------------------------
//    InputDto to Entity
//    ----------------------------------------------------------------------
    public CustomerCard inputDtoToEntity(CustomerCardInputDto cardInputDto){
        CustomerCard cardEntity = new CustomerCard();
        cardEntity.setId(cardInputDto.id);
        cardEntity.setCardNumber(cardInputDto.cardNumber);
        cardEntity.setCardStatus(cardInputDto.cardStatus);
        cardEntity.setAmountSpend(cardInputDto.amountSpend);
        return cardEntity;
    }
//    ----------------------------------------------------------------------
//    Entity to OutputDto
//    ----------------------------------------------------------------------
    public CustomerCardOutputDto entityToOutputDto(CustomerCard customerCard){
        CustomerCardOutputDto cardOutputDto = new CustomerCardOutputDto();
        cardOutputDto.id = customerCard.getId();
        cardOutputDto.cardNumber = customerCard.getCardNumber();
        cardOutputDto.cardStatus = customerCard.getCardStatus();
        return cardOutputDto;
    }
}