package com.example.sparkle.controllers;

import com.example.sparkle.dtos.inputDto.WorkScheduleInputDto;
import com.example.sparkle.dtos.outputDto.WorkScheduleOutputDto;
import com.example.sparkle.services.WorkScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/workschedules")
public class WorkScheduleController {
//    Instance Variables
    private final WorkScheduleService workScheduleService;

//    Constructor
    public WorkScheduleController(WorkScheduleService workScheduleService){
        this.workScheduleService = workScheduleService;

    }
//    MAPPINGS:
//    ----------------------------------------------------------------------
//    Post
//    ----------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<Object> createWorkSchedule(@Valid @RequestBody WorkScheduleInputDto workScheduleInputDto, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                stringBuilder.append(fieldError.getField() + ": ");
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        }
        Long newWorkScheduleDto = workScheduleService.createWorkSchedule(workScheduleInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newWorkScheduleDto ).toUriString());
        workScheduleInputDto.id = newWorkScheduleDto;
        return ResponseEntity.created(uri).body("Workschedule with id: " + newWorkScheduleDto + " is succesfully created.");
    }
//    ----------------------------------------------------------------------
//    Get
//    ----------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<WorkScheduleOutputDto> readOneWorkScheduleById(@PathVariable Long id){
        WorkScheduleOutputDto workScheduleOutputDto = workScheduleService.readOneWorkScheduleId(id);
        return ResponseEntity.ok().body(workScheduleOutputDto);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<WorkScheduleOutputDto>> readAllWorkSchedulesByDate(@PathVariable LocalDate date){
        List<WorkScheduleOutputDto> workScheduleDtoList = workScheduleService.readAllWorkSchedulesByDate(date);
        return ResponseEntity.ok().body(workScheduleDtoList);
    }

    @GetMapping
    public ResponseEntity<List<WorkScheduleOutputDto>> readAllWorkSchedule(){
        List<WorkScheduleOutputDto> workScheduleDtoList = workScheduleService.readAllWorkSchedules();
        return ResponseEntity.ok().body(workScheduleDtoList);
    }
//    ----------------------------------------------------------------------
//    Put
//    ----------------------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOneWorkSchedule(@Valid @RequestBody WorkScheduleInputDto workScheduleInputDto, @PathVariable Long id, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                stringBuilder.append(fieldError.getField() + ": ");
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        }
        WorkScheduleOutputDto workScheduleOutputDto = workScheduleService.updateOneWorkSchedule(workScheduleInputDto, id);
        return ResponseEntity.accepted().body(workScheduleOutputDto);
    }

    @PutMapping("/{id}/users/{userId}")
    public ResponseEntity<String> assignUserToWorkSchedules(@PathVariable Long id, @PathVariable String userId){
        String assignedWorkSchedule = workScheduleService.assignUserToWorkSchedules(id, userId);
        return ResponseEntity.ok().body(assignedWorkSchedule);
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOneWorkScheduleById(@PathVariable Long id){
        workScheduleService.deleteOneWorkScheduleId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}