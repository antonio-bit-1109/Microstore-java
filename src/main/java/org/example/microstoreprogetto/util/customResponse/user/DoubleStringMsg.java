package org.example.microstoreprogetto.util.customResponse.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DoubleStringMsg {

    private String message;

    private String token;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String msg2) {
        this.token = msg2;
    }

    public DoubleStringMsg(String m1, String m2) {
        setMessage(m1);
        setToken(m2);
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
