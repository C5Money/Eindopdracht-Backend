package com.example.sparkle.dtos.outputDto;


import com.example.sparkle.models.User;

import java.time.LocalDate;


public class WorkScheduleOutputDto {
//    DTO Variables
    public Long id;
    public LocalDate startDate;
    public LocalDate endDate;
    public Integer hoursPerWeek;
    public User user;
}
