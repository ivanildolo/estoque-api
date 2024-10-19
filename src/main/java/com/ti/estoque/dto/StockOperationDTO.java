package com.ti.estoque.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockOperationDTO {

    @NotNull(message = "O ID do produto é obrigatório.")
    private Long productId;

    @Min(value = 1, message = "A quantidade deve ser maior que zero.")
    private int quantity;
}
