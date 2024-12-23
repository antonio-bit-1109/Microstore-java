package org.example.microstoreprogetto.util.enums.statoordine;

public enum STATOORDINE {

    PENDING("PENDING"),
    PROCESSING("PROCESSING"),
    SHIPPED("SHIPPED"),
    COMPLETED("COMPLETED");

    //'PENDING', 'PROCESSING', 'SHIPPED', 'COMPLETED')

    private final String statoOrdine;

    private STATOORDINE(String statoOrdine) {
        this.statoOrdine = statoOrdine;
    }

    public String getStatoOrdine() {
        return statoOrdine;
    }
}
