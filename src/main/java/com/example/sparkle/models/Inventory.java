package com.example.sparkle.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer quantity;

//    Relations
    @OneToMany(mappedBy = "inventoryItem")
    @JsonIgnore
    private List<Product> products;
}
