package org.example.microstoreprogetto.util.customResponse.user;

import org.example.microstoreprogetto.USERS.DTO.StandardUserDTO;
import org.example.microstoreprogetto.USERS.entity.Users;
import org.example.microstoreprogetto.util.base_dto.BaseDTO;

public class UserMessageResponse {
    private BaseDTO user;
    private String Message;

    public void setMessage(String message) {
        Message = message;
    }

    public void setUser(BaseDTO user) {
        this.user = user;
    }

    public UserMessageResponse(BaseDTO u, String msg) {
        setUser(u);
        setMessage(msg);
    }

    public String getMessage() {
        return Message;
    }

    public BaseDTO getUser() {
        return user;
    }
}
