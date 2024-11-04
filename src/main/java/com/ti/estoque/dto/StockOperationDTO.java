package com.ti.estoque.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockOperationDTO {

    @NotNull(message = "O ID do produto é obrigatório.")
    @JsonProperty("product_id")
    private Long productId;

    @Min(value = 1, message = "A quantidade deve ser maior que zero.")
    private int quantity;
}
