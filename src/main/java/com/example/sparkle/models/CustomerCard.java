package com.example.sparkle.models;

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
    @GeneratedValue
    private Long id;
    private String cardNumber;
    private BigDecimal amountSpend;
    private CardStatus cardStatus;

//    Relations
    @OneToMany(mappedBy = "customerCard")
    private List<User> users;

    @OneToMany(mappedBy = "customerCard")
    private List<Product> products;

}
