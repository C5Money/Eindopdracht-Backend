package com.example.sparkle.dtos.outputDto;

import com.example.sparkle.models.CardStatus;
import com.example.sparkle.models.Product;

import java.util.ArrayList;
import java.util.List;


public class CustomerCardOutputDto {
//    DTO Variables
    public Long id;
    public String cardNumber;
    public CardStatus cardStatus;

//    Relaties
    public List<Product> productsBought = new ArrayList<>();
}
