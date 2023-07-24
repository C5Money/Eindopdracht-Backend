package com.example.sparkle.repositories;

import com.example.sparkle.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserNameContainingIgnoreCase(String userName);

}
