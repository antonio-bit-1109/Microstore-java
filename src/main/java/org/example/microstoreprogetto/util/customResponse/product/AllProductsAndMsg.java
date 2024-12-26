package org.example.microstoreprogetto.util.customResponse.product;

import org.aspectj.weaver.ast.Literal;
import org.example.microstoreprogetto.PRODUCTS.DTO.StandardProductDTO;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;

import java.util.List;

public class AllProductsAndMsg {
    private List<BaseDTO> listaProdotti;
    private String msg;


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setListaProdotti(List<BaseDTO> listaProdotti) {
        this.listaProdotti = listaProdotti;
    }

    //costrutt
    public AllProductsAndMsg(List<BaseDTO> listaProd, String msg) {
        setMsg(msg);
        setListaProdotti(listaProd);
    }

    public String getMsg() {
        return msg;
    }

    public List<BaseDTO> getListaProdotti() {
        return listaProdotti;
    }
}
