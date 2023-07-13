package com.example.sparkle.dtos.inputDto;

import com.example.sparkle.models.CardStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerCardInputDto {
    //    DTO Variables
    public Long id;

    @NotBlank(message = "Cardnumber is required")
    @Size(max = 8, message = "Cardnumber cannot exceed 8 characters")
    public String cardNumber;

    @NotNull( message = "The amopunt spend is required.")
    public Double amountSpend;

    public CardStatus cardStatus;
}
