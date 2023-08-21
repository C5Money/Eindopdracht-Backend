package com.example.sparkle.configs;

import com.example.sparkle.filters.JwtRequestFilter;
import com.example.sparkle.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
//    Instance Variables
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
//    Constructor
    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

// Authenticatie met customUserDetailsService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

// PasswordEncoderBean. Deze kun je overal in je applicatie injecteren waar nodig.
// Je kunt dit ook in een aparte configuratie klasse zetten.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

// Authorizatie met jwt
    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        //JWT token authentication
        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                // endpoint workschedules
                .requestMatchers(HttpMethod.POST, "/workschedules").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/workschedules/{id}").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/workschedules/date/{date}").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/workschedules").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/workschedules/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/workschedules/{id}/user/{userId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/workschedules/{id}").hasRole("ADMIN")
                // endpoint users
                .requestMatchers(HttpMethod.POST, "/users").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/{username}").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/{username}/customercard/{cardNumber}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/{username}").hasRole("ADMIN")
                // endpoint customercards
                .requestMatchers(HttpMethod.POST, "/customercards").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/customercards/{cardNumber}").hasAnyRole("USER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/customercards/status/{cardNumber}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/customercards").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/customercards/{cardNumber}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/customercards/{cardNumber}").hasRole("ADMIN")
                // endpoint products
                .requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/products/{articleNumber}").hasAnyRole("EMPLOYER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/products/name/{name}").hasAnyRole("EMPLOYER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/products").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/products/{articleNumber}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/products/{articleNumber}/inventory/{inventoryId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/products/{articleNumber}/cardnumber/{cardnumber}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/products/{cardNumber}").hasRole("ADMIN")
                // endpoint inventories
                .requestMatchers(HttpMethod.POST, "/inventories").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/inventories/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/inventories/name/{name}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/inventories").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/inventories/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/inventories/{id}").hasRole("ADMIN")
                // endpoint authentication
                .requestMatchers("/authenticated").authenticated()
                .requestMatchers("/authenticate").permitAll()/*allen dit punt mag toegankelijk zijn voor niet ingelogde gebruikers*/
                .anyRequest().denyAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}