package org.example.microstoreprogetto.util.customResponse.cart;

import org.example.microstoreprogetto.CARTS.DTO.getCart.CartGET_DTO;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;

public class CartMsgDTO {

    private CartGET_DTO cart;
    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCart(CartGET_DTO cart) {
        this.cart = cart;
    }

    public CartMsgDTO(CartGET_DTO cart, String msg) {
        this.cart = cart;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public CartGET_DTO getCart() {
        return cart;
    }
}
