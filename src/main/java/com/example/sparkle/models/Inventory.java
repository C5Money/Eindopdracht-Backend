package com.example.sparkle.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "inventories")
public class Inventory {
//    Instance Variables
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Integer quantity;

//    Relations
    @OneToMany(mappedBy = "inventoryItem")
    private List<Product> products;
}
