package com.example.sparkle.dtos.outputDto;

import com.example.sparkle.models.Product;

import java.util.ArrayList;
import java.util.List;

public class InventoryOutputDto {
//    Instance Variables
    public Long id;
    public String name;
    public String description;
    public Integer quantity;

//    Relaties
    public List<Product> products = new ArrayList<>();

}
