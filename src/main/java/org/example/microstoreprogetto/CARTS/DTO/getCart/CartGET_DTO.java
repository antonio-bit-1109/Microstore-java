package org.example.microstoreprogetto.CARTS.DTO.getCart;

import java.util.ArrayList;
import java.util.List;

public class CartGET_DTO {

    private Long idCarrello;
    private String createdAt;
    private String owner;
    private List<CartItemsDTO> listaProdotti;

    public void setIdCarrello(Long idCarrello) {
        this.idCarrello = idCarrello;
    }

    public void setListaProdotti(List<CartItemsDTO> listaProdotti) {
        this.listaProdotti = listaProdotti;
    }

    public void AddToListaProdotti(CartItemsDTO item) {
        getListaProdotti().add(item);
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    //costrutt
    public CartGET_DTO() {
        setListaProdotti(new ArrayList<>());
    }

    public Long getIdCarrello() {
        return idCarrello;
    }

    public List<CartItemsDTO> getListaProdotti() {
        return listaProdotti;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getOwner() {
        return owner;
    }
}
