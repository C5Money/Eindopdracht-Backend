package com.example.sparkle.repositories;

import com.example.sparkle.models.CustomerCard;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerCardRepository extends JpaRepository<CustomerCard, Long> {



}
