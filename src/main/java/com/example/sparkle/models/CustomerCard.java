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

//    @OneToOne()
//    @JoinColumn(name = "users", nullable = false)
//    private User user;
//
//    @OneToMany(mappedBy = "products")
//    private List<Product> products;
}
