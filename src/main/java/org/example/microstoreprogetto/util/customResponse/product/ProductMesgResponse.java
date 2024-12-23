package org.example.microstoreprogetto.util.customResponse.product;

import org.example.microstoreprogetto.PRODUCTS.DTO.StandardProductDTO;

public class ProductMesgResponse {

    private String msg;
    private StandardProductDTO prodDTO;

    //costrutt
    public ProductMesgResponse(StandardProductDTO prodDTO, String msg) {
        setMsg(msg);
        setProdDTO(prodDTO);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setProdDTO(StandardProductDTO prodDTO) {
        this.prodDTO = prodDTO;
    }

    public String getMsg() {
        return msg;
    }

    public StandardProductDTO getProdDTO() {
        return prodDTO;
    }
}
