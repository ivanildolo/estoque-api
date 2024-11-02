package com.ti.estoque.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ti.estoque.models.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProductDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotNull(message = "Id da categoria é obrigatória")
    @JsonProperty("category_id")
    private Long categoryId;

    @NotNull(message = "Quantidade em estoque é obrigatória")
    @Min(value = 0, message = "A quantidade em estoque não pode ser negativa")
    private Integer quantity;

    @NotNull(message = "Preço é obrigatório")
    @Min(value = 0, message = "O preço não pode ser negativo")
    private BigDecimal price;

    @NotBlank(message = "Localização no depósito é obrigatória")
    private String location;

    @NotBlank(message = "Descrição é obrigatória")
    private String description;

}
