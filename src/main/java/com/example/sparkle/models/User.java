package com.example.sparkle.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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


}
