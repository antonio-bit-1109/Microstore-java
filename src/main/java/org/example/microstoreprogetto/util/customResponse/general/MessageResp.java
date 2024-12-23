package org.example.microstoreprogetto.util.customResponse.general;

public class MessageResp {
    private String Message;

    public void setMessage(String message) {
        Message = message;
    }

    //costutt
    public MessageResp(String msg) {
        setMessage(msg);
    }

    public String getMessage() {
        return Message;
    }
}
