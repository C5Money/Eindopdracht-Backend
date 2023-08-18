package com.example.sparkle.controllers;


import com.example.sparkle.dtos.inputDto.UserInputDto;
import com.example.sparkle.dtos.outputDto.UserOutputDto;
import com.example.sparkle.exceptions.BadRequestException;
import com.example.sparkle.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
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
    public ResponseEntity<Object> createUser( @RequestBody UserInputDto userInputDto) {;
        String newUsername = userService.createUser(userInputDto);
        userService.addAuthority(newUsername, "ROLE_USER");

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(uri).body("User with username: " + newUsername + " is successfully created.");
    }
//    ----------------------------------------------------------------------
//    Get
//    ----------------------------------------------------------------------
    @GetMapping(value = "/{username}")
    public ResponseEntity<UserOutputDto> getUser(@PathVariable("username") String username) {
        UserOutputDto optionalUser = userService.readUser(username);
        return ResponseEntity.ok().body(optionalUser);
    }

    @GetMapping
    public ResponseEntity<List<UserOutputDto>> getUsers() {
        List<UserOutputDto> userDtoList = userService.readUsers();
        return ResponseEntity.ok().body(userDtoList);
    }
//    ----------------------------------------------------------------------
//    Put
//    ----------------------------------------------------------------------
    @PutMapping(value = "/{username}")
    public ResponseEntity<Object> updateUser(@PathVariable("username") String username, @RequestBody UserInputDto userInputDto) {
        userService.updateUser(username, userInputDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{username}/customercard/{cardNumber}")
    public ResponseEntity<String> assignCustomerCardToUser(@PathVariable String username, @PathVariable Long cardNumber) {
        String userOutputDto = userService.assignCustomerCardToUser(username, cardNumber);
        return ResponseEntity.ok().body(userOutputDto);
    }
//    ----------------------------------------------------------------------
//    Delete
//    ----------------------------------------------------------------------
    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }
//    ----------------------------------------------------------------------
//    Authority
//    ----------------------------------------------------------------------
    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }

}