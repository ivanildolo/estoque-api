package com.ti.estoque.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProductDTO {

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

}
