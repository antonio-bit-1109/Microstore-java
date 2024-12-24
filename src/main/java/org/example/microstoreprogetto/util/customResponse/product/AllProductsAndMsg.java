package org.example.microstoreprogetto.util.customResponse.product;

import org.aspectj.weaver.ast.Literal;
import org.example.microstoreprogetto.PRODUCTS.DTO.StandardProductDTO;

import java.util.List;

public class AllProductsAndMsg {
    private List<StandardProductDTO> listaProdotti;
    private String msg;


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setListaProdotti(List<StandardProductDTO> listaProdotti) {
        this.listaProdotti = listaProdotti;
    }

    //costrutt
    public AllProductsAndMsg(List<StandardProductDTO> listaProd, String msg) {
        setMsg(msg);
        setListaProdotti(listaProd);
    }

    public String getMsg() {
        return msg;
    }

    public List<StandardProductDTO> getListaProdotti() {
        return listaProdotti;
    }
}
