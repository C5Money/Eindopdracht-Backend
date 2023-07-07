package com.example.sparkle.repositories;

import com.example.sparkle.models.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {


    List<WorkSchedule> findAllByStartDate(LocalDate date);



}
