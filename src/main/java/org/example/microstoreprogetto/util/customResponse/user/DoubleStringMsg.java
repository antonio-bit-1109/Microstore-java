package org.example.microstoreprogetto.util.customResponse.user;

public class DoubleStringMsg {

    private String msg1;
    private String token;

    public void setMsg1(String msg1) {
        this.msg1 = msg1;
    }

    public void setMsg2(String msg2) {
        this.token = msg2;
    }

    public DoubleStringMsg(String m1, String m2) {
        setMsg1(m1);
        setMsg2(m2);
    }

    public String getMsg1() {
        return msg1;
    }

    public String getToken() {
        return token;
    }
}
