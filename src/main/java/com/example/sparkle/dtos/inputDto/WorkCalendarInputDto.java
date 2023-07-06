package com.example.sparkle.dtos.inputDto;


import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class WorkCalendarInputDto {
//    DTO Variables
    public Long id;

    @NotBlank(message = "Start date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate startDate;

    @NotBlank(message = "End date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate endDate;

    @NotBlank(message = "Begin time is required")
    @DateTimeFormat(pattern = "HH:mm:ss")
    public LocalTime beginTime;

    @NotBlank(message = "End time is required")
    @DateTimeFormat(pattern = "HH:mm:ss")
    public LocalTime endTime;
}
