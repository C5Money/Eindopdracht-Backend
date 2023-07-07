package com.example.sparkle.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
@Entity
@Table(name = "work_calendar")
public class WorkSchedule {
//    Instance Variables
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime beginTime;
    private LocalTime endTime;
}
