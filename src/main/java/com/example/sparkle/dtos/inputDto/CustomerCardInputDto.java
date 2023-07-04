package com.example.sparkle.dtos.inputDto;

import com.example.sparkle.models.CardStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CustomerCardInputDto {
    //    DTO Variables
    public Long id;
    @NotBlank(message = "Cardnumber is required")
    @Size(max = 15, message = "Cardnumber cannot exceed 15 characters")
    public String cardNumber;
    @NotBlank(message = "Amount is required")
    @DecimalMin(value = "0.0", message = "Price must be a positive number")
    public BigDecimal amountSpend;

    public CardStatus cardStatus;
}
