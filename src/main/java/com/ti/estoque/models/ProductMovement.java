package com.ti.estoque.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ti.estoque.enums.MovementType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Table(name = "product_movement")
@Entity(name = "product_movement")
@NoArgsConstructor
public class ProductMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "O product é obrigatória")
    private Product product;


    @NotNull(message = "A propriedade quantity_changed é obrigatória")
    @JsonProperty("quantity")
    private int quantity;

    @NotNull(message = "A propriedade movementType é obrigatória")
    @JsonProperty("movement_type")
    private MovementType movementType;

    @JsonProperty("movement_date")
    private LocalDateTime movementDate;

    @PrePersist
    public void prePersist() {
        this.movementDate = LocalDateTime.now();
    }


    public ProductMovement(Product product, int quantity, MovementType movementType) {
        this.product = product;
        this.quantity = quantity;
        this.movementType = movementType;
        ;
    }
}
