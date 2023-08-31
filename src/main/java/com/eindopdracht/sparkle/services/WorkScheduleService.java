package com.eindopdracht.sparkle.services;

import com.eindopdracht.sparkle.exceptions.ResourceNotFoundException;
import com.eindopdracht.sparkle.repositories.UserRepository;
import com.eindopdracht.sparkle.dtos.inputDto.WorkScheduleInputDto;
import com.eindopdracht.sparkle.dtos.outputDto.WorkScheduleOutputDto;
import com.eindopdracht.sparkle.models.User;
import com.eindopdracht.sparkle.models.WorkSchedule;
import com.eindopdracht.sparkle.repositories.WorkScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkScheduleService {
//    Instance Variables
    private final WorkScheduleRepository workScheduleRepository;
    private final UserRepository userRepository;
//    Constructor
    public WorkScheduleService(WorkScheduleRepository workScheduleRepository, UserRepository userRepository){
        this.workScheduleRepository = workScheduleRepository;
        this.userRepository = userRepository;
    }
//    CRUD:
//    ----------------------------------------------------------------------
//    Create
//    ----------------------------------------------------------------------
    public Long createWorkSchedule(WorkScheduleInputDto workScheduleInputDto){
        if(workScheduleRepository.existsById(workScheduleInputDto.id)) throw new ResourceNotFoundException("Workschedule " + workScheduleInputDto.id + " already exists.");
        WorkSchedule newWorkScheduleEntity =  inputDtoToEntity(workScheduleInputDto);
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

    public String assignUserToWorkSchedules(Long id, String userId){
        Optional<WorkSchedule> optionalWorkSchedule = workScheduleRepository.findById(id);
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalWorkSchedule.isEmpty() && optionalUser.isEmpty()){
            throw new ResourceNotFoundException("User with id: " + userId + " or workschedule with id: " + id + " do not exist.");
        }
        WorkSchedule updatableWorkSchedule = optionalWorkSchedule.get();
        User updatableUser = optionalUser.get();
        updatableWorkSchedule.setUser(updatableUser);
        WorkSchedule updatedWorkschedule = workScheduleRepository.save(updatableWorkSchedule);
        return "Workschedule with id: " + id + " has succesfully been assigned to user-id:" + userId + ".";
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    public void deleteOneWorkScheduleId(Long id){
        Optional<WorkSchedule> optionalWorkSchedule = workScheduleRepository.findById(id);
        if(optionalWorkSchedule.isEmpty()){
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
        workScheduleOutputDto.user = workSchedule.getUser();
        return workScheduleOutputDto;
    }
}

