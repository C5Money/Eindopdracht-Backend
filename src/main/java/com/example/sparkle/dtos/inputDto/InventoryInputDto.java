package com.example.sparkle.dtos.inputDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryInputDto {
//    Instance Variables
    public Long id;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    public String description;

    @NotNull(message = "Sellstock is required")
    public String sellStock;

    @NotNull(message = "Available stock is required")
    public String orderStock;
//    private List<Product> products;
}
