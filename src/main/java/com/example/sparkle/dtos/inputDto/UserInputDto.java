package com.example.sparkle.dtos.inputDto;

import com.example.sparkle.models.Authority;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.*;


import java.util.Set;

public class UserInputDto {

    public String username;
    public String password;
    public Boolean enabled;
    public String apikey;
    public String email;
    @JsonSerialize
    public Set<Authority> authorities;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getApikey() {
        return apikey;
    }

    public String getEmail() {
        return email;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}


//    @NotBlank(message = "Username is required")
//    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
//    public String userName;
//
//    @NotBlank
//    @Size(min=6, max=30)
//    public String password;
//
//    @NotBlank(message = "Email is required")
//    @Email
//    public String email;
//
//    @NotBlank(message = "First name is required")
//    @Size(max = 50, message = "First name cannot exceed 50 characters")
//    public String firstName;
//
//    @NotBlank(message = "Last name is required")
//    @Size(max = 50, message = "Last name cannot exceed 50 characters")
//    public String lastName;
//
//    @NotBlank(message = "Address is required")
//    @Size(max = 100, message = "Address cannot exceed 100 characters")
//    public String address;
//
//    @NotBlank(message = "Zip code is required")
//    public String zipCode;
//
//    @NotBlank(message = "Phone number is required")
//    @Pattern(regexp = "\\d{10}", message = "Phone number must be a 10-digit number")
//    public String phoneNumber;

