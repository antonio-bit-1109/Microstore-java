package org.example.microstoreprogetto.util.customResponse.user;

import org.example.microstoreprogetto.USERS.DTO.StandardUserDTO;
import org.example.microstoreprogetto.USERS.entity.Users;

public class UserMessageResponse {
    private StandardUserDTO user;
    private String Message;

    public void setMessage(String message) {
        Message = message;
    }

    public void setUser(StandardUserDTO user) {
        this.user = user;
    }

    public UserMessageResponse(StandardUserDTO u, String msg) {
        setUser(u);
        setMessage(msg);
    }

    public String getMessage() {
        return Message;
    }

    public StandardUserDTO getUser() {
        return user;
    }
}
