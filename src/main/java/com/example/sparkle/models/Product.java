package com.example.sparkle.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
//    Instance Variables
    @Id
    @GeneratedValue
    private Long id;
    private String productName;
    private String articleNumber;
    private BigDecimal unitPrice;
    private Double availableStock;
    private String category;


//    Relations
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "customerCard_id", nullable = false)
    private CustomerCard customerCard;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JsonIgnore
//    @JoinColumn(name = "inventory_item")
//    private Inventory inventoryItem;
//



}
