package com.example.sparkle.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//public class AuthenticationController {
//
//    @GetMapping("/hello")
//    public String sayHello(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if(authentication.getPrincipal() instanceof UserDetails ){
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            return "Hello" + userDetails.getUsername();
//        } else {
//            return "Hello Stranger";
//        }
//    }
//}