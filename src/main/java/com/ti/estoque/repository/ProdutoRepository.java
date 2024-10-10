package com.ti.estoque.repository;

import com.ti.estoque.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Product, Long> {
}

