package org.example.microstoreprogetto.CARTS.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.microstoreprogetto.ORDERS.DTO.ProductInfoDTO;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;

public class CreateCarrelloDTO {

    @NotNull
    private Long idUser;

    @NotEmpty
    private ArrayList<ProductInfoDTO> listaProdotti;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public ArrayList<ProductInfoDTO> getListaProdotti() {
        return listaProdotti;
    }

    public void setListaProdotti(ArrayList<ProductInfoDTO> listaProdotti) {
        this.listaProdotti = listaProdotti;
    }
}
