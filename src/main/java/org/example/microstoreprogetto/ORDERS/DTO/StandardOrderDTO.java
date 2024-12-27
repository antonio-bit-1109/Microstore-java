package org.example.microstoreprogetto.ORDERS.DTO;

import org.example.microstoreprogetto.USERS.entity.Users;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;

import java.sql.Time;
import java.sql.Timestamp;

public class StandardOrderDTO extends BaseDTO {

    private String idOrdine;
    private String usernameAcquirente;
    private String status;
    private String totalespesa;
    private String oraordine;

    public void setId(String idOrdine) {
        this.idOrdine = idOrdine;
    }

    public String getId() {
        return idOrdine;
    }

    public String getUser() {
        return usernameAcquirente;
    }

    public void setUser(Users userEntity) {
        this.usernameAcquirente = userEntity.getName();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal() {
        return totalespesa;
    }

    public void setTotal(String totalespesa) {
        this.totalespesa = totalespesa;
    }

    public String getCreated_at() {
        return oraordine;
    }

    public void setCreated_at(String oraordine) {
        this.oraordine = oraordine;
    }
}
