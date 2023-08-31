package com.eindopdracht.sparkle.dtos.outputDto;


import com.eindopdracht.sparkle.models.User;

import java.time.LocalDate;


public class WorkScheduleOutputDto {
//    DTO Variables
    public Long id;
    public LocalDate startDate;
    public LocalDate endDate;
    public Integer hoursPerWeek;
    public User user;
}
