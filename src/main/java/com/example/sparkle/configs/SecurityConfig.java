package com.example.sparkle.configs;

import com.example.sparkle.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
//
//@Configuration
////@EnableWebSecurity
//public class SecurityConfig {
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder){
//        InMemoryUserDetailsManager man = new InMemoryUserDetailsManager();
//
//        UserDetails u1 = User
//                .withUsername("Clide")
//                .password(encoder.encode("Pinas"))
//                .roles("USER")
//                .build();
//        man.createUser(u1);
//
//        UserDetails u2 = User
//                .withUsername("peer")
//                .password(encoder.encode("mans"))
//                .roles("ADMIN")
//                .build();
//        man.createUser(u2);
//        return man;
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity
//                .csrf().disable()
//                .httpBasic().disable()
//                .cors()
//                .and()
//                .authorizeHttpRequests()

//                .requestMatchers(HttpMethod.DELETE, "/secret").hasRole("ADMIN")
//                .requestMatchers("/hello").permitAll()
//                .anyRequest().denyAll()
//                .and()
//                .sessionManagement().sessionCreationPolicy(sessionCreationPolicy.STATELESS);
//
//        httpSecurity
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}
