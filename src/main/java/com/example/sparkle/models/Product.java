package com.example.sparkle.models;

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

//    @ManyToOne
//    @JoinColumn(name = "inventory_item_id")
//    private InventoryItem inventoryItem;
//
//    @ManyToOne
//    @JoinColumn(name = "customer_card_id")
//    private CustomerCard customerCard;
}
