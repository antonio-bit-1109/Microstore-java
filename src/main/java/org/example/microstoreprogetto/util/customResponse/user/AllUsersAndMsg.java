package org.example.microstoreprogetto.util.customResponse.user;

import org.example.microstoreprogetto.USERS.DTO.StandardUserDTO;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;

import java.util.List;

public class AllUsersAndMsg {
    private List<BaseDTO> listaUtenti;
    private String msg;


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setListaUtenti(List<BaseDTO> listaUtenti) {
        this.listaUtenti = listaUtenti;
    }

    //costrutt
    public AllUsersAndMsg(List<BaseDTO> listaUtenti, String msg) {
        setMsg(msg);
        setListaUtenti(listaUtenti);
    }

    public String getMsg() {
        return msg;
    }

    public List<BaseDTO> getListaUtenti() {
        return listaUtenti;
    }
}
