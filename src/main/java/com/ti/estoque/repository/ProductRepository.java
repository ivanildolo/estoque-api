package com.ti.estoque.repository;

import com.ti.estoque.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByStockQuantityLessThan(int minimumQuantity);

    List<Product> findByStockQuantityGreaterThan(int maxQuantity);

    List<Product> findBycreationDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}

