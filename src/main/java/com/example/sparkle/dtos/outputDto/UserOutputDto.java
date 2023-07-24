package com.example.sparkle.dtos.outputDto;

import com.example.sparkle.models.CustomerCard;
import com.example.sparkle.models.WorkSchedule;

import java.util.List;

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
    public List<WorkSchedule> workSchedules;
}
