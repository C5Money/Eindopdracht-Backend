package com.example.sparkle.dtos.inputDto;

import jakarta.validation.constraints.*;

public class UserInputDto {
//    Instance Variables
    public Long userId;
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
    public String userName;

    @NotBlank(message = "Email is required")
    @Email
    public String email;

    @NotBlank
    @Size(min=6, max=30)
    public String password;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    public String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    public String lastName;

    @NotBlank(message = "Address is required")
    @Size(max = 100, message = "Address cannot exceed 100 characters")
    public String address;

    @NotBlank(message = "Zip code is required")
    public String zipCode;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be a 10-digit number")
    public String phoneNumber;
}
