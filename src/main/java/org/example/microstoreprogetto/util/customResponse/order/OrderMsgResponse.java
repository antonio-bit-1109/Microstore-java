package org.example.microstoreprogetto.util.customResponse.order;

import org.example.microstoreprogetto.ORDERS.DTO.StandardOrderDTO;

public class OrderMsgResponse {

    private StandardOrderDTO orderDTO;
    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setOrderDTO(StandardOrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    //costrutt
    public OrderMsgResponse(StandardOrderDTO orderDTO, String msg) {
        setOrderDTO(orderDTO);
        setMsg(msg);
    }

    public String getMsg() {
        return msg;
    }

    public StandardOrderDTO getOrderDTO() {
        return orderDTO;
    }
}
