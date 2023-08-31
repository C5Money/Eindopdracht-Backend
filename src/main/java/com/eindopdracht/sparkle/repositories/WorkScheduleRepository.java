package com.eindopdracht.sparkle.repositories;

import com.eindopdracht.sparkle.models.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {

    List<WorkSchedule> findAllByStartDate(LocalDate date);

}
