package com.example.sparkle.services;

import com.example.sparkle.dtos.inputDto.CustomerCardInputDto;
import com.example.sparkle.dtos.inputDto.InventoryInputDto;
import com.example.sparkle.dtos.outputDto.CustomerCardOutputDto;
import com.example.sparkle.dtos.outputDto.ProductOutputDto;
import com.example.sparkle.exceptions.ResourceNotFoundException;
import com.example.sparkle.models.CardStatus;
import com.example.sparkle.models.CustomerCard;
import com.example.sparkle.models.Inventory;
import com.example.sparkle.models.WorkSchedule;
import com.example.sparkle.repositories.CustomerCardRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
            throw new ResourceNotFoundException("Cardnumber: " + cardNumber + " is invalid or doesn't exist.");
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
    public CustomerCardOutputDto updateOneCustomerCard(CustomerCardInputDto cardInputDto, String cardNumber) {
        Optional<CustomerCard> optionalCustomerCard = customerCardRepository.findCustomerCardByCardNumber(cardNumber);
        if (optionalCustomerCard.isPresent() ) {
            CustomerCard updatableCustomerCard = optionalCustomerCard.get();
            CustomerCard updatedCustomerCard = updateInputDtoToEntity(cardInputDto, updatableCustomerCard);

            customerCardRepository.save(updatedCustomerCard);
            return entityToOutputDto(updatedCustomerCard);
        } else {
            throw new ResourceNotFoundException("Customercard with cardnumber: " + cardInputDto.cardNumber + " did not update");
        }
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    public void deleteOneCustomerCardById(Long id){
        Optional<CustomerCard> optionalCustomerCard =customerCardRepository.findById(id);
        if(optionalCustomerCard.isEmpty()){
            throw new ResourceNotFoundException("This product: " + id + " is already deleted or doesn't exist.");
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
//            sumProductsBought( cardEntity.getProducts() );
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
        if(cardInputDto.cardNumber != null ){
            if(cardEntity.getCardNumber().equals(cardInputDto.cardNumber)){
                throw new ResourceNotFoundException("Customercard with cardnumber: " + cardInputDto.cardNumber + " already exists.");
            } else {
                cardEntity.setCardNumber(cardInputDto.cardNumber);
            }
        }
//        if(cardInputDto.cardStatus != null){
//            cardEntity.setCardStatus(cardInputDto.cardStatus);
//        }
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
        cardOutputDto.amountSpend = customerCard.getAmountSpend();
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

//    public Object sumProductsBought(List<ProductOutputDto> amount ){
//        int sum = 0;
//        for(ProductOutputDto spend : amount){
//
//            sum += spend.unitPrice;
//        }
//        return sum;
//    }
}