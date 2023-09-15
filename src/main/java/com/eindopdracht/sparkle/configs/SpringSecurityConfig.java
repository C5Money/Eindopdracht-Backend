package com.eindopdracht.sparkle.configs;

import com.eindopdracht.sparkle.filters.JwtRequestFilter;
import com.eindopdracht.sparkle.services.CustomUserDetailsService;
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
                .requestMatchers(HttpMethod.POST, "/workschedules").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/workschedules/{id}").hasAnyRole("EMPLOYER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/workschedules/date/{date}").hasAnyRole("EMPLOYER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/workschedules").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/workschedules/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/workschedules/{id}/users/{userName}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/workschedules/{id}").hasRole("ADMIN")
                // endpoint users
                .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/{username}").hasAnyRole("USER", "EMPLOYER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/{username}/customercards/{cardNumber}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/users/{username}/authorities").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/{username}/authorities").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/{username}/authorities/{authority}").hasRole("ADMIN")
                // endpoint customercards
                .requestMatchers(HttpMethod.POST, "/customercards").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/customercards/{cardNumber}").hasAnyRole("USER", "EMPLOYER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/customercards/status/{cardStatus}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/customercards").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/customercards/{cardNumber}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/customercards/{cardNumber}").hasRole("ADMIN")
                // endpoint products
                .requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/products/{articleNumber}").hasAnyRole("EMPLOYER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/products/name/{name}").hasAnyRole("EMPLOYER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/products").hasAnyRole("EMPLOYER","ADMIN")
                .requestMatchers(HttpMethod.PUT, "/products/{articleNumber}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/products/{articleNumber}/inventories/{inventoryId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/products/{articleNumber}/cardnumber/{cardnumber}").hasAnyRole("EMPLOYER","ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/products/{articleNumber}").hasRole("ADMIN")
                // endpoint inventories
                .requestMatchers(HttpMethod.POST, "/inventories").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/inventories/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/inventories/name/{name}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/inventories").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/inventories/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/inventories/{id}").hasRole("ADMIN")
                // endpoint filestorage
                .requestMatchers(HttpMethod.POST, "file/upload").hasAnyRole("USER", "EMPLOYER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "file/download/{id}").hasAnyRole("USER", "EMPLOYER", "ADMIN")
                // endpoint authentication
                .requestMatchers("/authenticated").authenticated()
                .requestMatchers("/authenticate").permitAll()/*alleen dit punt mag toegankelijk zijn voor niet ingelogde gebruikers*/
                .anyRequest().denyAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}