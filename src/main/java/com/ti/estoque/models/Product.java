package com.ti.estoque.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Table(name = "products")
@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("name")
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @JsonProperty("category")
    @NotBlank(message = "Categoria é obrigatória")
    private String category;

    @JsonProperty("stock_quantity")
    @NotNull(message = "Quantidade em estoque é obrigatória")
    @Min(value = 0, message = "A quantidade em estoque não pode ser negativa")
    private Integer stockQuantity;

    @JsonProperty("price")
    @NotNull(message = "Preço é obrigatório")
    @Min(value = 0, message = "O preço não pode ser negativo")
    private Double price;

    @JsonProperty("warehouse_location")
    @NotBlank(message = "Localização no depósito é obrigatória")
    private String warehouseLocation;

    @JsonProperty("description")
    @NotBlank(message = "Descrição é obrigatória")
    private String description;

    @JsonProperty("creation_date")
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductMovement> movements = new ArrayList<>();;

    @PrePersist
    public void prePersist() {
        this.creationDate = LocalDateTime.now();
    }

    public Product(String name, String category, Integer stockQuantity, Double price, String warehouseLocation, String description) {
        this.name = name;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.warehouseLocation = warehouseLocation;
        this.description = description;
    }

}
