package com.example.sparkle.repositories;

import com.example.sparkle.models.CardStatus;
import com.example.sparkle.models.CustomerCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CustomerCardRepository extends JpaRepository<CustomerCard, Long> {

    Optional<CustomerCard> findCustomerCardByCardNumber(String cardNumber);
    List<CustomerCard> findAllByCardStatus(CardStatus cardStatus);
    Optional<CustomerCard> findByCardNumber(String cardNumber);
    Boolean existsByCardNumber(String cardNumber);


}
