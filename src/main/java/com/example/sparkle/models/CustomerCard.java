package com.example.sparkle.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "customer_cards")
public class CustomerCard {
//    Instance Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;
    private double amountSpend;
    private CardStatus cardStatus;


//    Relations
    @OneToMany(mappedBy = "customerCard")
    @JsonIgnore
    private List<Product> products;

//    @OneToMany(mappedBy = "customerCard")
//    private List<User> users;


//    Instance Methods


}
