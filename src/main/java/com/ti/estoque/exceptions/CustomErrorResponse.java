package com.ti.estoque.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomErrorResponse {
    private int status;
    private String message;

    public CustomErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
