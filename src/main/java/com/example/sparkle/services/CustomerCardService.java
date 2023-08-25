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
        Optional<CustomerCard> optionalCustomerCard = customerCardRepository.findById(cardInputDto.cardNumber);
        if(optionalCustomerCard.isPresent()){
            throw new ResourceNotFoundException("Card id " + cardInputDto.cardNumber + " already exists. ");
        }
        CustomerCard newCustomerCardEntity = inputDtoToEntity(cardInputDto);
        customerCardRepository.save(newCustomerCardEntity);
        return newCustomerCardEntity.getCardNumber();
    }
//    ----------------------------------------------------------------------
//    Read
//    ----------------------------------------------------------------------
    public CustomerCardOutputDto readOneCustomerCardByCardNumber(Long cardNumber){
        Optional<CustomerCard> optionalCustomerCard = customerCardRepository.findById(cardNumber);
        if(optionalCustomerCard.isEmpty()){
            throw new ResourceNotFoundException("Card id: " + cardNumber + " is invalid or doesn't exist.");
        }
        return entityToOutputDto(optionalCustomerCard.get());
    }

    public List<CustomerCardOutputDto> readAllCustomerCardsByCardStatus(CardStatus cardStatus) {
        List<CustomerCard> cardStatusList = customerCardRepository.findAllByCardStatus(cardStatus);
        List<CustomerCardOutputDto> customerCardOutputDtoList = new ArrayList<>();
        if(cardStatusList.isEmpty()){
            throw new ResourceNotFoundException("There are no customercards found with " + cardStatus + " level.");
        }
        for(CustomerCard customerCardEntity : cardStatusList){
            customerCardOutputDtoList.add(entityToOutputDto(customerCardEntity));
        }
        return customerCardOutputDtoList;
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
    public CustomerCardOutputDto updateOneCustomerCard(CustomerCardInputDto cardInputDto, Long cardNumber) {
        Optional<CustomerCard> optionalCustomerCard = customerCardRepository.findById(cardNumber);
        if(optionalCustomerCard.isPresent() ) {
            CustomerCard updatableCustomerCard = optionalCustomerCard.get();
            CustomerCard updatedCustomerCard = updateInputDtoToEntity(cardInputDto, updatableCustomerCard);

            customerCardRepository.save(updatedCustomerCard);
            return entityToOutputDto(updatedCustomerCard);
        } else {
            throw new ResourceNotFoundException("Customercard with cardnumber: " + cardNumber + " did not update.");
        }
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    public void deleteOneCustomerCardById(Long cardNumber){
        Optional<CustomerCard> optionalCustomerCard =customerCardRepository.findById(cardNumber);
        if(optionalCustomerCard.isEmpty()){
            throw new ResourceNotFoundException("This customercard with id: " + cardNumber + " is already deleted or doesn't exist.");
        }
        CustomerCard foundCustomerCard = optionalCustomerCard.get();
        customerCardRepository.delete(foundCustomerCard);
    }

//    MAPPERS:
//    ----------------------------------------------------------------------
//    InputDto to Entity
//    ----------------------------------------------------------------------
    public CustomerCard inputDtoToEntity(CustomerCardInputDto cardInputDto){
        CustomerCard cardEntity = new CustomerCard();
        if(cardInputDto.cardNumber != null){
            cardEntity.setCardNumber(cardInputDto.cardNumber);
        }

        if(cardInputDto.amountSpend != null){
            cardEntity.setAmountSpend(cardInputDto.amountSpend);
//            cardEntity.setAmountSpend(sumAmountSpend(cardEntity.getProducts());
        }

        if(cardInputDto.cardStatus == null){
         cardEntity.setCardStatus(automateSetCardStatus(cardInputDto.amountSpend));
        }

        return cardEntity;
    }

    public CustomerCard updateInputDtoToEntity(CustomerCardInputDto cardInputDto, CustomerCard cardEntity){
        if(cardInputDto.cardNumber != null ){
            if(cardEntity.getCardNumber().equals(cardInputDto.cardNumber)){
                throw new ResourceNotFoundException("Customercard with card-number: " + cardInputDto.cardNumber + " already exists.");
            } else {
                cardEntity.setCardNumber(cardInputDto.cardNumber);
            }
        }

        if(cardInputDto.amountSpend != null){
            cardEntity.setAmountSpend(cardInputDto.amountSpend);
        }

        if(cardInputDto.cardStatus != null){
            cardEntity.setCardStatus(automateSetCardStatus(cardInputDto.amountSpend));
        }

        return cardEntity;
    }
//    ----------------------------------------------------------------------
//    Entity to OutputDto
//    ----------------------------------------------------------------------
    public CustomerCardOutputDto entityToOutputDto(CustomerCard customerCard){
        CustomerCardOutputDto cardOutputDto = new CustomerCardOutputDto();
        cardOutputDto.cardNumber = customerCard.getCardNumber();
        cardOutputDto.amountSpend = customerCard.getAmountSpend();
        cardOutputDto.cardStatus = customerCard.getCardStatus();
        cardOutputDto.products = customerCard.getProducts();
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