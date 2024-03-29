package com.eindopdracht.sparkle.dtos.inputDto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@AllArgsConstructor
public class WorkScheduleInputDto {
//    DTO Variables
    public Long id;

    @NotNull(message = "Start date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    public LocalDate startDate;

    @NotNull(message = "End date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    public LocalDate endDate;

    @NotNull(message = "hours per week is required")
    public Integer hoursPerWeek;



}
