package com.example.sparkle.services;

import com.example.sparkle.dtos.inputDto.CustomerCardInputDto;
import com.example.sparkle.dtos.outputDto.CustomerCardOutputDto;
import com.example.sparkle.exceptions.ResourceNotFoundException;
import com.example.sparkle.models.CardStatus;
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
    public Long createCustomerCard(CustomerCardInputDto cardInputDto){
        Optional<CustomerCard> optionalCustomerCard = customerCardRepository.findCustomerCardByCardNumber(cardInputDto.cardNumber);
        if(optionalCustomerCard.isPresent()){
            throw new ResourceNotFoundException("Cardnumber " + cardInputDto.cardNumber + " already exists. ");
        }
        CustomerCard newCustomerCardEntity = inputDtoToEntity(cardInputDto);
        customerCardRepository.save(newCustomerCardEntity);
        return newCustomerCardEntity.getId();
    }
//    ----------------------------------------------------------------------
//    Read
//    ----------------------------------------------------------------------
    public CustomerCardOutputDto readOneCustomerCardByCardNumber(String cardNumber){
        Optional<CustomerCard> optionalCustomerCard = customerCardRepository.findCustomerCardByCardNumber(cardNumber);
        if(optionalCustomerCard.isEmpty()){
            throw new ResourceNotFoundException("This cardnumber: " + cardNumber + " is invalid or doesn't exist.");
        }
        return entityToOutputDto(optionalCustomerCard.get());
    }

    public CustomerCardOutputDto readAllCustomerCardsByCardStatus(CardStatus cardStatus){
        Optional<CustomerCard> optionalCustomerCard = customerCardRepository.findAllByCardStatus(cardStatus);
        if(optionalCustomerCard.isEmpty()){
            throw new ResourceNotFoundException("There are no customercards found with " + cardStatus + " level.");
        }
        return entityToOutputDto(optionalCustomerCard.get());
    }

    public List<CustomerCardOutputDto> readAllCustomerCards(){
        List<CustomerCard> customerCardList = customerCardRepository.findAll();
        List<CustomerCardOutputDto> customerCardOutputDtoList = new ArrayList<>();
        if(customerCardList.isEmpty()){
            throw new ResourceNotFoundException("Customercards not found.");
        }
        for ( CustomerCard customerCardEntity : customerCardList){
            customerCardOutputDtoList.add(entityToOutputDto(customerCardEntity));
        }
        return customerCardOutputDtoList;
    }
//    ----------------------------------------------------------------------
//    Update
//    ----------------------------------------------------------------------
    public CustomerCardOutputDto updateOneCustomerCard(CustomerCardInputDto cardInputDto, Long id){
        CustomerCard optionalCustomerCard = customerCardRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("This id: " + id + " does not exist."));
        CustomerCard updatedCustomerCard = updateInputDtoToEntity(cardInputDto, optionalCustomerCard);
        customerCardRepository.save(updatedCustomerCard);
        return entityToOutputDto(updatedCustomerCard);
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    public void deleteOneCustomerCard(Long id){
        Optional<CustomerCard> optionalCustomerCard = customerCardRepository.findById(id);
        if (optionalCustomerCard.isEmpty() || id <= 0){
            throw new ResourceNotFoundException("This id: " + id + " is invalid or doesn't exist.");
        }
        customerCardRepository.deleteById(id);
    }

//    MAPPERS:
//    ----------------------------------------------------------------------
//    InputDto to Entity
//    ----------------------------------------------------------------------
    public CustomerCard inputDtoToEntity(CustomerCardInputDto cardInputDto){
        CustomerCard cardEntity = new CustomerCard();
        if(cardInputDto.id != null){
            cardEntity.setId(cardInputDto.id);
        }
        if(cardInputDto.cardNumber != null){
            cardEntity.setCardNumber(cardInputDto.cardNumber);
        }
        if(cardInputDto.amountSpend != null){
            cardEntity.setAmountSpend(cardInputDto.amountSpend);
        }

        if(cardInputDto.cardStatus == null){
         cardEntity.setCardStatus(automateSetCardStatus(cardInputDto.amountSpend));
        }

        return cardEntity;
    }

    public CustomerCard updateInputDtoToEntity(CustomerCardInputDto cardInputDto, CustomerCard cardEntity){
        if(cardInputDto.id != null){
            cardEntity.setId(cardInputDto.id);
        }
        if(cardInputDto.cardNumber != null){
            cardEntity.setCardNumber(cardInputDto.cardNumber);
        }
        if(cardInputDto.cardStatus != null){
            cardEntity.setCardStatus(cardInputDto.cardStatus);
        }
        if(cardInputDto.amountSpend != null){
            cardEntity.setAmountSpend(cardInputDto.amountSpend);
        }
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
        cardOutputDto.productsBought = customerCard.getProducts();
        return cardOutputDto;
    }

//    ----------------------------------------------------------------------
//    Automate Cardstatus
//    ----------------------------------------------------------------------
    public CardStatus automateSetCardStatus(double amountSpend){
        if(amountSpend < 2500){
            return CardStatus.BRONZE;
        } else if (amountSpend >= 2500 && amountSpend < 6000) {
            return CardStatus.SILVER;
        } else if (amountSpend >= 6000 &&  amountSpend < 10000) {
            return CardStatus.GOLD;
        } else {
            return CardStatus.PLATINUM;
        }
    }
}