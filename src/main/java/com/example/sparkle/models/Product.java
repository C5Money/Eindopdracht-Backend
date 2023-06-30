package com.example.sparkle.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
//    Instance Variables
    @Id
    @GeneratedValue
    private Long id;
    private String productName;
    private String articleNumber;
    private Double price;
    private Double availableStock;
    private String category;


}
