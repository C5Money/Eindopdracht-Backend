package com.example.sparkle.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "inventory")
public class Inventory {
//    Instance Variables
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Integer quantity;

//    @OneToMany(mappedBy = "inventory_item")
//    private List<Product> products;
}
