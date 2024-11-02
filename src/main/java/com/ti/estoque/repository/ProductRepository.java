package com.ti.estoque.repository;

import com.ti.estoque.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByQuantityLessThan(int minimumQuantity);

    List<Product> findByQuantityGreaterThan(int maxQuantity);

    List<Product> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Product> findByCreatedAtBetweenAndNameContainingIgnoreCase(LocalDateTime start, LocalDateTime end, String name);
}

