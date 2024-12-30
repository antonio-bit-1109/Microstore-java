package org.example.microstoreprogetto.CARTS.DTO;

import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Check;

public class DeleteCartDTO {

    @NotNull
    private Long idCarrello;
    @NotNull
    private Long idUser;

    public void setIdCarrello(Long idCarrello) {
        this.idCarrello = idCarrello;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdCarrello() {
        return idCarrello;
    }

    public Long getIdUser() {
        return idUser;
    }
}
