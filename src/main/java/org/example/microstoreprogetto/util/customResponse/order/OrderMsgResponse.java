package org.example.microstoreprogetto.util.customResponse.order;

import org.example.microstoreprogetto.ORDERS.DTO.StandardOrderDTO;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;

public class OrderMsgResponse {

    private BaseDTO orderDTO;
    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setOrderDTO(BaseDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    //costrutt
    public OrderMsgResponse(BaseDTO orderDTO, String msg) {
        setOrderDTO(orderDTO);
        setMsg(msg);
    }

    public String getMsg() {
        return msg;
    }

    public BaseDTO getOrderDTO() {
        return orderDTO;
    }
}
