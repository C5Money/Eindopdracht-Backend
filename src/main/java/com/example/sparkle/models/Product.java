package com.example.sparkle.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
//    Instance Variables
    @Id
    private Long articleNumber;

    private String productName;
    private Double unitPrice;
    private Double availableStock;
    private String category;


//    Relations
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "customerCard_id")
    private CustomerCard customerCard;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "inventory_item_id")
    private Inventory inventoryItem;

}
