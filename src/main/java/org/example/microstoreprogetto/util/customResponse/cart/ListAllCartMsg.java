package org.example.microstoreprogetto.util.customResponse.cart;

import org.example.microstoreprogetto.CARTS.DTO.getCart.CartGET_DTO;
import org.example.microstoreprogetto.util.base_dto.BasedDTO_GET;

import java.util.List;

public class ListAllCartMsg {
    private List<BasedDTO_GET> listaCarrelli;
    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setListaCarrelli(List<BasedDTO_GET> listaCarrelli) {
        this.listaCarrelli = listaCarrelli;
    }

    //costrutt
    public ListAllCartMsg(List<BasedDTO_GET> listacart, String msg) {
        setListaCarrelli(listacart);
        setMsg(msg);
    }

    public String getMsg() {
        return msg;
    }

    public List<BasedDTO_GET> getListaCarrelli() {
        return listaCarrelli;
    }
}
