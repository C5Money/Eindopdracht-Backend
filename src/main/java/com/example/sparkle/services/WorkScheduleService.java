package com.example.sparkle.services;

import com.example.sparkle.dtos.inputDto.WorkScheduleInputDto;
import com.example.sparkle.dtos.outputDto.WorkScheduleOutputDto;
import com.example.sparkle.exceptions.ResourceNotFoundException;
import com.example.sparkle.models.User;
import com.example.sparkle.models.WorkSchedule;
import com.example.sparkle.repositories.UserRepository;
import com.example.sparkle.repositories.WorkScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkScheduleService {
//    Instance Variables
    private final WorkScheduleRepository workScheduleRepository;
//    private final UserRepository userRepository;
//    Constructor
    public WorkScheduleService(WorkScheduleRepository workworkScheduleRepository){
        this.workScheduleRepository = workworkScheduleRepository;
//        this.userRepository = userRepository;

    }
//    CRUD:
//    ----------------------------------------------------------------------
//    Create
//    ----------------------------------------------------------------------
    public Long createWorkSchedule(WorkScheduleInputDto workScheduleInputDto){
//        Optional<WorkSchedule> optionalWorkSchedule = workScheduleRepository.findById(workScheduleInputDto.id);
//        if(optionalWorkSchedule.isPresent()){
//            throw new ResourceNotFoundException("Workschedule " + workScheduleInputDto.id + " already exists.");
//        }

        WorkSchedule newWorkScheduleEntity =  inputDtoToEntity(workScheduleInputDto);

//        Optional<User> optionalUser = userRepository.findById(workScheduleInputDto.)

        workScheduleRepository.save(newWorkScheduleEntity);
        return newWorkScheduleEntity.getId();
    }
//    ----------------------------------------------------------------------
//    Read
//    ----------------------------------------------------------------------
    public WorkScheduleOutputDto readOneWorkScheduleId(Long id){
        Optional<WorkSchedule> optionalWorkSchedule = workScheduleRepository.findById(id);
        if(optionalWorkSchedule.isEmpty() || id <= 0){
            throw new ResourceNotFoundException("This id: " + id + " is invalid or doesn't exist.");
        }
        return entityToOutputDto(optionalWorkSchedule.get());
    }

    public List<WorkScheduleOutputDto> readAllWorkSchedulesByDate(LocalDate date){
        List<WorkSchedule> optionalWorkScheduleListByDate = workScheduleRepository.findAllByStartDate(date);
        List<WorkScheduleOutputDto> workScheduleOutputDtoList = new ArrayList<>();
        if(optionalWorkScheduleListByDate.isEmpty()){
            throw new ResourceNotFoundException("Work schedule(s) not found.");
        } else {
            for ( WorkSchedule workScheduleEntity : optionalWorkScheduleListByDate){
                workScheduleOutputDtoList.add(entityToOutputDto(workScheduleEntity));
            }
        }
        return workScheduleOutputDtoList;
    }

    public List<WorkScheduleOutputDto> readAllWorkSchedules(){
        List<WorkSchedule> optionalWorkScheduleList = workScheduleRepository.findAll();
        List<WorkScheduleOutputDto> workScheduleOutputDtoList = new ArrayList<>();
        if(optionalWorkScheduleList.isEmpty()){
            throw new ResourceNotFoundException("Work schedules not found.");
        } else {
            for ( WorkSchedule workScheduleEntity : optionalWorkScheduleList){
                workScheduleOutputDtoList.add(entityToOutputDto(workScheduleEntity));
            }
        }
        return workScheduleOutputDtoList;
    }
//    ----------------------------------------------------------------------
//    Update
//    ----------------------------------------------------------------------
    public WorkScheduleOutputDto updateOneWorkSchedule(WorkScheduleInputDto workScheduleInputDto, Long id){
        WorkSchedule optionalWorkSchedule = workScheduleRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("This id: " + id + " does not exist."));
        WorkSchedule updatedWorkSchedule = updateInputDtoToEntity(workScheduleInputDto, optionalWorkSchedule);
        workScheduleRepository.save(updatedWorkSchedule);
        return entityToOutputDto(updatedWorkSchedule);
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    public void deleteOneWorkScheduleId(Long id){
        Optional<WorkSchedule> optionalWorkSchedule = workScheduleRepository.findById(id);
        if(optionalWorkSchedule.isEmpty() || id <= 0){
            throw new ResourceNotFoundException("This product: " + id + " is already deleted or doesn't exist.");
        }
        workScheduleRepository.deleteById(id);
    }

//    MAPPERS:
//    ----------------------------------------------------------------------
//    InputDto to Entity
//    ----------------------------------------------------------------------
    public WorkSchedule inputDtoToEntity(WorkScheduleInputDto workScheduleInputDto){
        WorkSchedule workScheduleEntity = new WorkSchedule();
        if(workScheduleInputDto.id != null){
            workScheduleEntity.setId(workScheduleInputDto.id);
        }

        if(workScheduleInputDto.startDate != null){
            workScheduleEntity.setStartDate(workScheduleInputDto.startDate);
        }
        if(workScheduleInputDto.endDate != null){
            workScheduleEntity.setEndDate(workScheduleInputDto.endDate);
        }
        if(workScheduleInputDto.hoursPerWeek != null){
            workScheduleEntity.setHoursPerWeek(workScheduleInputDto.hoursPerWeek);
        }

        return workScheduleEntity;
    }

    public WorkSchedule updateInputDtoToEntity(WorkScheduleInputDto workScheduleInputDto, WorkSchedule workScheduleEntity){
//        if(workScheduleInputDto.id != null){
//            workScheduleEntity.setId(workScheduleInputDto.id);
//        }
        if(workScheduleInputDto.startDate != null){
            workScheduleEntity.setStartDate(workScheduleInputDto.startDate);
        }
        if(workScheduleInputDto.endDate != null){
            workScheduleEntity.setEndDate(workScheduleInputDto.endDate);
        }
        if(workScheduleInputDto.hoursPerWeek != null){
            workScheduleEntity.setHoursPerWeek(workScheduleInputDto.hoursPerWeek);
        }

        return workScheduleEntity;
    }
//    ----------------------------------------------------------------------
//    Entity to OutputDto
//    ----------------------------------------------------------------------
    public WorkScheduleOutputDto entityToOutputDto(WorkSchedule workSchedule){
        WorkScheduleOutputDto workScheduleOutputDto = new WorkScheduleOutputDto();
        workScheduleOutputDto.id = workSchedule.getId();
        workScheduleOutputDto.startDate = workSchedule.getStartDate();
        workScheduleOutputDto.endDate = workSchedule.getEndDate();
        workScheduleOutputDto.hoursPerWeek = workSchedule.getHoursPerWeek();
        return workScheduleOutputDto;
    }
}

