package com.example.sparkle.repositories;

import com.example.sparkle.models.CardStatus;
import com.example.sparkle.models.CustomerCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CustomerCardRepository extends JpaRepository<CustomerCard, Long> {

    Optional<CustomerCard> findCustomerCardByCardNumber(String cardNumber);
    Optional<CustomerCard> findAllByCardStatus(CardStatus cardStatus);

}
