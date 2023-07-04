package com.example.sparkle.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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
    private BigDecimal price;
    private Double availableStock;
    private String category;
}
