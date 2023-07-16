package com.example.sparkle.dtos.inputDto;

import com.example.sparkle.models.CustomerCard;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

//@Getter
//@Setter
@AllArgsConstructor
public class ProductInputDto {
//    DTO Variables
    public Long id;

    @NotBlank(message = "Product name is required")
    @Size(max = 50, message = "Product name cannot exceed 50 characters")
    public String productName;

    @NotNull(message = "Product article number is required")
    public String articleNumber;


    public Double unitPrice;

    @NotNull(message = "Available stock is required")
    @DecimalMin(value = "0.0", message = "Available stock must be a positive number")
    public Double availableStock;

    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category cannot exceed 50 characters")
    public String category;

//    Relaties

    public Long customerCardId;
//    public Long inventoryItemId;

}
