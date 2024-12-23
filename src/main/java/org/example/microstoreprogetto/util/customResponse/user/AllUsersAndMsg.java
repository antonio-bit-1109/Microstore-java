package org.example.microstoreprogetto.util.customResponse.user;

import org.example.microstoreprogetto.USERS.DTO.StandardUserDTO;
import org.example.microstoreprogetto.USERS.entity.Users;

import java.util.List;

public class AllUsersAndMsg {
    private List<StandardUserDTO> listaUtenti;
    private String msg;


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setListaUtenti(List<StandardUserDTO> listaUtenti) {
        this.listaUtenti = listaUtenti;
    }

    //costrutt
    public AllUsersAndMsg(List<StandardUserDTO> listaUtenti, String msg) {
        setMsg(msg);
        setListaUtenti(listaUtenti);
    }

    public String getMsg() {
        return msg;
    }

    public List<StandardUserDTO> getListaUtenti() {
        return listaUtenti;
    }
}
