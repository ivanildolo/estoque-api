package com.ti.estoque.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchDTO {
    @NotNull(message = "A data inicial é obrigatória.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "A data inicial deve estar no formato AAAA-MM-DD.")
    private String startDate;

    @NotNull(message = "A data final é obrigatória.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "A data final deve estar no formato AAAA-MM-DD.")
    private String endDate;

    private String name;
}
