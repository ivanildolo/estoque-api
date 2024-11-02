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
@Table(name = "movements")
@Entity(name = "movement")
@NoArgsConstructor
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("movement_type")
    @Enumerated(EnumType.STRING)
    private MovementType movementType;

    @Column(nullable = false)
    private String location;

    @JsonProperty("movement_date")
    private LocalDateTime movementDate;

    @PrePersist
    public void prePersist() {
        this.movementDate = LocalDateTime.now();
    }


    public Movement(Product product, int quantity, MovementType movementType, String location) {
        this.product = product;
        this.quantity = quantity;
        this.movementType = movementType;
        this.location = location;
    }
}
