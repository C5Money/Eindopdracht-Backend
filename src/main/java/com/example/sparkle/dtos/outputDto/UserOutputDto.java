package com.example.sparkle.dtos.outputDto;

import com.example.sparkle.models.Authority;
import com.example.sparkle.models.CustomerCard;
import com.example.sparkle.models.WorkSchedule;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.Set;


public class UserOutputDto {

    public String username;
    public String password;
//    public Boolean enabled;
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

//    public Boolean getEnabled() {
//        return enabled;
//    }

    public String getApikey() {
        return apikey;
    }

    public String getEmail() {
        return email;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }


//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setEnabled(Boolean enabled) {
//        this.enabled = enabled;
//    }
//
//    public void setApikey(String apikey) {
//        this.apikey = apikey;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setAuthorities(Set<Authority> authorities) {
//        this.authorities = authorities;
//    }
}