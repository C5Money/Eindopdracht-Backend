package com.eindopdracht.sparkle.dtos.outputDto;

import com.eindopdracht.sparkle.models.CustomerCard;
import com.eindopdracht.sparkle.models.Authority;
import com.eindopdracht.sparkle.models.WorkSchedule;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.Set;


public class UserOutputDto {

    public String username;
    public String password;
    public String apikey;
    public String email;
    @JsonSerialize
    public Set<Authority> authorities;

    public CustomerCard customerCard;
    public List<WorkSchedule> workSchedule;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public String getApikey() {
        return apikey;
    }

    public String getEmail() {
        return email;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }


}