package com.example.sparkle.dtos.inputDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class InventoryInputDto {
//    Instance Variables
    public Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Product name cannot exceed 50 characters")
    public String name;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    public String description;

    @NotNull(message = "quantity is required")
    public Integer quantity;

}
