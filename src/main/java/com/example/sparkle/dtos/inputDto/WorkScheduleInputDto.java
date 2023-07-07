package com.example.sparkle.dtos.inputDto;

import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
public class WorkScheduleInputDto {
//    DTO Variables
    public Long id;

    @NotNull(message = "Start date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate startDate;

    @NotNull(message = "End date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate endDate;

    @NotNull(message = "Begin time is required")
    @DateTimeFormat(pattern = "HH:mm")
    public LocalTime beginTime;

    @NotNull(message = "End time is required")
    @DateTimeFormat(pattern = "HH:mm")
    public LocalTime endTime;
}
