package com.example.sparkle.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "inventory")
public class Inventory {
//    Instance Variables
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private String sellStock;
    private String orderStock;
//    private List<Product> products;
}
