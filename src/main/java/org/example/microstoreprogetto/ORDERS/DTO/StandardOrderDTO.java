package org.example.microstoreprogetto.ORDERS.DTO;

import org.example.microstoreprogetto.util.base_dto.BaseDTO;

import java.sql.Time;
import java.sql.Timestamp;

public class StandardOrderDTO extends BaseDTO {

    private Long idOrdine;
    private String usernameAcquirente;
    private String status;
    private Double totalespesa;
    private Time oraordine;

    public void setIdOrdine(Long idOrdine) {
        this.idOrdine = idOrdine;
    }

    public Long getIdOrdine() {
        return idOrdine;
    }

    public String getUsernameAcquirente() {
        return usernameAcquirente;
    }

    public void setUsernameAcquirente(String usernameAcquirente) {
        this.usernameAcquirente = usernameAcquirente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalespesa() {
        return totalespesa;
    }

    public void setTotalespesa(Double totalespesa) {
        this.totalespesa = totalespesa;
    }

    public Time getOraordine() {
        return oraordine;
    }

    public void setOraordine(Time oraordine) {
        this.oraordine = oraordine;
    }
}
