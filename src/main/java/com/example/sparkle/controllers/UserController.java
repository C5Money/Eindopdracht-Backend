package com.example.sparkle.controllers;

import com.example.sparkle.dtos.inputDto.UserInputDto;
import com.example.sparkle.dtos.outputDto.UserOutputDto;
import com.example.sparkle.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
//    Instance Variables
    private final UserService userService;
//    Constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }
//    MAPPINGS:
//    ----------------------------------------------------------------------
//    Post
//    ----------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserInputDto userInputDto, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                stringBuilder.append(fieldError.getField() + ": ");
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        }
        Long newUserDto = userService.createUser(userInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + newUserDto ).toUriString());
        userInputDto.userId = newUserDto;
        return ResponseEntity.created(uri).body("User with user id: " + userInputDto.userId + " is succesfully created.");
    }
//    ----------------------------------------------------------------------
//    Get
//    ----------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<UserOutputDto> readOneUserById(@PathVariable Long id){
        UserOutputDto userOutputDto = userService.readOneUserById(id);
        return ResponseEntity.ok().body(userOutputDto);
    }

    @GetMapping
    public ResponseEntity<List<UserOutputDto>> readAllWorkSchedule(){
        List<UserOutputDto> userDtoList = userService.readAllUsers();
        return ResponseEntity.ok().body(userDtoList);
    }
//    ----------------------------------------------------------------------
//    Put
//    ----------------------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@Valid @PathVariable Long id, @RequestBody UserInputDto userInputDto, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError fieldError : bindingResult.getFieldErrors()){
                stringBuilder.append(fieldError.getField() + ": ");
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        }
        UserOutputDto userOutputDto = userService.updateOneUser(userInputDto, id);
        return ResponseEntity.ok().body(userOutputDto);
    }

    @PutMapping("/{id}/customercard/{cardNumber}")
    public ResponseEntity<UserOutputDto> assignCustomerCardToUser(@PathVariable Long id, @PathVariable Long cardNumber){
        UserOutputDto userOutputDto = userService.assignCustomerCardToUser(id, cardNumber);
        return ResponseEntity.ok().body(userOutputDto);
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOneUserById(@PathVariable Long id){
        userService.deleteOneUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
