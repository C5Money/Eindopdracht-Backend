package com.eindopdracht.sparkle.repositories;

import com.eindopdracht.sparkle.models.CardStatus;
import com.eindopdracht.sparkle.models.CustomerCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerCardRepository extends JpaRepository<CustomerCard, Long> {

    List<CustomerCard> findAllByCardStatus(CardStatus cardStatus);

}
