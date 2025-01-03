package org.example.microstoreprogetto.ORDERS.DTO.getOrder;

import org.example.microstoreprogetto.PRODUCTS.DTO.StandardProductDTO;

public class OrderItemsDTO {
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
