package org.example.microstoreprogetto.util.customResponse.product;

import org.example.microstoreprogetto.util.base_dto.BaseDTO;

public class ProductResponse {

    private String msg;
    private BaseDTO prodDTO;

    //costrutt
    public ProductResponse(BaseDTO prodDTO, String msg) {
        setMsg(msg);
        setProdDTO(prodDTO);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setProdDTO(BaseDTO prodDTO) {
        this.prodDTO = prodDTO;
    }

    public String getMsg() {
        return msg;
    }

    public BaseDTO getProdDTO() {
        return prodDTO;
    }
}
