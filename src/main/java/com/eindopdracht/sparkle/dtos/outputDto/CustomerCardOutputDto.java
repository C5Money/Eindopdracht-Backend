package com.eindopdracht.sparkle.dtos.outputDto;

import com.eindopdracht.sparkle.models.CardStatus;
import com.eindopdracht.sparkle.models.Product;

import java.util.ArrayList;
import java.util.List;


public class CustomerCardOutputDto {
//    DTO Variables
    public Long cardNumber;
    public Double amountSpend;
    public CardStatus cardStatus;


//    Relaties
    public List<Product> products = new ArrayList<>();
}
