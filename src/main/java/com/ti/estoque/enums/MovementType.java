package com.ti.estoque.enums;

public enum MovementType {
    ENTRY("Entrada de produto no estoque"),
    EXIT("Saída de produto do estoque");

    private String description;

    MovementType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
