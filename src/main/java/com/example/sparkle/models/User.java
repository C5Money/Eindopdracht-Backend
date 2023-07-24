package com.example.sparkle.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
//    Instance Variables
    @Id
    @GeneratedValue
    private Long id;

    private String userName;
    private String email;
    private String password;

    private String firstName;
    private String lastName;
    private String zipCode;
    private String address;
    private String phoneNumber;


//    Relations
    @OneToOne(cascade = CascadeType.ALL)
    private CustomerCard customerCard;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<WorkSchedule> workSchedules;
}
