package com.eindopdracht.sparkle.repositories;


import com.eindopdracht.sparkle.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

//    Optional<User> findByUserNameContainingIgnoreCase(String userName);

}
