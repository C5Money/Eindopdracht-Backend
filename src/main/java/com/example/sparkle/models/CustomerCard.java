package com.example.sparkle.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "customer_cards")
public class CustomerCard {
//    Instance Variables
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String cardNumber;
    private double amountSpend;
    private CardStatus cardStatus;
}
