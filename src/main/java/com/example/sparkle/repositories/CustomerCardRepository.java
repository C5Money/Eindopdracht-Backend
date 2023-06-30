package com.example.sparkle.repositories;

import com.example.sparkle.models.CustomerCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerCardRepository extends JpaRepository<CustomerCard, Long> {

//    @Query("SELECT max(c.id) FROM CustomerCard c")
//    Long findCustomerCardById;

}
