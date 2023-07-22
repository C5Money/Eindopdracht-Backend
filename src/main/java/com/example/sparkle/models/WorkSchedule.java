package com.example.sparkle.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    @OneToMany(mappedBy = "workSchedule")
    private List<User> users;
}
