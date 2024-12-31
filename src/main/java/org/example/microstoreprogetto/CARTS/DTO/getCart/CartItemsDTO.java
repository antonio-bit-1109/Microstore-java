package org.example.microstoreprogetto.CARTS.DTO.getCart;

import org.example.microstoreprogetto.PRODUCTS.DTO.StandardProductDTO;

public class CartItemsDTO {

    private Long idProdotto;
    private StandardProductDTO prodotto;

    public void setProdotto(StandardProductDTO prodotto) {
        this.prodotto = prodotto;
    }


    public void setIdProdotto(Long idProdotto) {
        this.idProdotto = idProdotto;
    }


    public Long getIdProdotto() {
        return idProdotto;
    }

    public StandardProductDTO getProdotto() {
        return prodotto;
    }
}
