package com.example.sparkle.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customer_cards")
public class CustomerCard {
//    Instance Variables
    @Id
    private Long cardNumber;

    private Double amountSpend;
    private CardStatus cardStatus;

//    Relations
    @OneToOne(mappedBy = "customerCard")
    @JoinColumn(nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "customerCard")
    @JsonIgnore
    private List<Product> products;
}
