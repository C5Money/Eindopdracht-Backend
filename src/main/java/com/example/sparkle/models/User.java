package com.example.sparkle.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
//    Instance Variables
    @Id
    private String userName;
    private String password;

    private String email;
    private String firstName;
    private String lastName;
    private String zipCode;
    private String address;
    private String phoneNumber;


//    Relations
    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private CustomerCard customerCard;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<WorkSchedule> workSchedules;
}