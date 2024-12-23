package org.example.microstoreprogetto.PRODUCTS.DTO;

import jakarta.validation.constraints.NotNull;

public class AddStockProductDTO {

    @NotNull(message = "id obbligatorio")
    private Long id;

    @NotNull(message = "stock obbligatorio")
    private Integer stock;

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStock() {
        return stock;
    }

    public Long getId() {
        return id;
    }
}
