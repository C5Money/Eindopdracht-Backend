package com.example.sparkle.dtos.outputDto;

import com.example.sparkle.models.CustomerCard;

public class UserOutputDto {
//    DTO Variables
    public Long id;
    public String userName;
    public String firstName;
    public String lastName;
    public String zipCode;
    public String address;
    public String phoneNumber;

    //    Relaties
    public CustomerCard customerCard;
}
