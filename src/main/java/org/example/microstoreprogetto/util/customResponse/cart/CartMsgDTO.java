package org.example.microstoreprogetto.util.customResponse.cart;

import org.example.microstoreprogetto.CARTS.DTO.getCart.CartGET_DTO;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;
import org.example.microstoreprogetto.util.base_dto.BasedDTO_GET;

public class CartMsgDTO {

    private BasedDTO_GET cart;
    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCart(BasedDTO_GET cart) {
        this.cart = cart;
    }

    public CartMsgDTO(BasedDTO_GET cart, String msg) {
        this.cart = cart;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public BasedDTO_GET getCart() {
        return cart;
    }
}
