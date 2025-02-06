package org.example.microstoreprogetto.CARTS.DTO;

import jakarta.validation.constraints.NotNull;
import org.example.microstoreprogetto.ORDERS.DTO.ProductInfoDTO;

import java.util.ArrayList;


// l oggetto in arrivo dal client dovrà contenere idutente che sta effettuando operazione,
// carrello nel qual inserire il prodotto in più
// i dati effettivi del prodotto, oggetto con idProdotto, quantità e prezzo
public class AddProdTCartExistedDTO {

    @NotNull
    private Long idUser;

    @NotNull
    private Long idCarrello;

    @NotNull
    private ArrayList<ProductInfoDTO> listaProdotti;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdCarrello() {
        return idCarrello;
    }

    public void setIdCarrello(Long idCarrello) {
        this.idCarrello = idCarrello;
    }

    public ArrayList<ProductInfoDTO> getListaProdotti() {
        return listaProdotti;
    }

    public void setListaProdotti(ArrayList<ProductInfoDTO> listaProdotti) {
        this.listaProdotti = listaProdotti;
    }
}
