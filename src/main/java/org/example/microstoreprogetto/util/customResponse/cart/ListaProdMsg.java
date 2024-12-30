package org.example.microstoreprogetto.util.customResponse.cart;

import org.example.microstoreprogetto.CARTS.DTO.StandardCartDTO;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;

import java.util.List;

public class ListaProdMsg {

    private String Msg;
    private BaseDTO dataCarrello;

    public void setMsg(String msg) {
        Msg = msg;
    }

    public void setListaProdottiCarrello(StandardCartDTO datiCarrello) {
        this.dataCarrello = datiCarrello;
    }

    public ListaProdMsg(BaseDTO datiCarrello, String msg) {
        this.dataCarrello = datiCarrello;
        this.Msg = msg;
    }

    public String getMsg() {
        return Msg;
    }

    public BaseDTO getDataCarrello() {
        return dataCarrello;
    }
}
