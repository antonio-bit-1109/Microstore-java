package org.example.microstoreprogetto.ORDERS.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class EditOrdineDTO extends CreateOrderDTO {

    @NotEmpty
    private String status;
    private Long orderId;

    //costrutt
    public EditOrdineDTO() {
        super();
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setSTATUS(String STATUS) {
        this.status = STATUS;
    }

    public String getSTATUS() {
        return status;
    }


    public Long getOrderId() {
        return orderId;
    }
}
