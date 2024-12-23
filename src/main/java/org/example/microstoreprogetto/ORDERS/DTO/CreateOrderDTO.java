package org.example.microstoreprogetto.ORDERS.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

public class CreateOrderDTO {

    @NotNull
    private String userId;

    @NotEmpty
    private ArrayList<ProductInfoDTO> listaProdotti;

    @NotNull
    @Min(1)
    private Double total;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<ProductInfoDTO> getListaProdotti() {
        return listaProdotti;
    }

    public void setListaProdotti(ArrayList<ProductInfoDTO> listaProdotti) {
        this.listaProdotti = listaProdotti;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
