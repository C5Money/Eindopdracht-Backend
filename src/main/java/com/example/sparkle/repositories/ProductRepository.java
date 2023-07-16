package com.example.sparkle.repositories;

import com.example.sparkle.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductNameContainingIgnoreCase(String productName);

    Optional<Product> findByArticleNumberContainingIgnoreCase(String articleNumber);


}
