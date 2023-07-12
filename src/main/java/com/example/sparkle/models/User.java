package com.example.sparkle.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
//    Instance Variables
    @Id
    @GeneratedValue
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String zipCode;
    private String Address;
    private String phoneNumber;
    private String email;

//    Relations
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private CustomerCard customerCard;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "work_schedule_id")
    private WorkSchedule workSchedule;
}
