package com.example.sparkle.dtos.inputDto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class ProductInputDto {
//    DTO Variables
    @NotBlank(message = "Product name is required")
    @Size(max = 50, message = "Product name cannot exceed 50 characters")
    public String productName;

    @NotNull(message = "Product article number is required")
    public Long articleNumber;

    @DecimalMin(value = "0.00", inclusive = true, message = "Unit price is required.")
    public Double unitPrice;

    @NotNull(message = "Available stock is required")
    @DecimalMin(value = "0.0", message = "Available stock must be a positive number")
    public Double availableStock;

    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category cannot exceed 50 characters")
    public String category;

//    Relaties
    public Long customerCardId;
    public Long inventoryItemId;

}
