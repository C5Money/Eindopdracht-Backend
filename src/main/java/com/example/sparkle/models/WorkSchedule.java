package com.example.sparkle.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "work_schedules")
public class WorkSchedule {
//    Instance Variables
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private int hoursPerWeek;

//    Relations
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
