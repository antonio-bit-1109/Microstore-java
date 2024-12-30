package org.example.microstoreprogetto.CARTS.DTO;

import org.example.microstoreprogetto.CARTS.entity.Cart_items;
import org.example.microstoreprogetto.ORDERS.DTO.ProductInfoDTO;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;

import java.util.ArrayList;
import java.util.List;

public class StandardCartDTO extends BaseDTO {

    private String idCarrello;
    private String usernameUtente;
    private List<Cart_items> CartItems;

    //SETTER
    public void setCartItems(List<Cart_items> cartItems) {
        CartItems = cartItems;
    }

    // setter dell id del carrello
    public void setId(String idCarrello) {
        this.idCarrello = idCarrello;
    }

    public void setUser(Users userObj) {
        this.usernameUtente = userObj.getName();
    }


    //GETTER
    public String getIdCarrello() {
        return idCarrello;
    }

    public List<Cart_items> getCartItems() {
        return CartItems;
    }

    public String getUsernameUtente() {
        return usernameUtente;
    }

}
