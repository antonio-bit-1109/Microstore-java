package org.example.microstoreprogetto.util.customResponse.order;

import org.example.microstoreprogetto.util.base_dto.BasedDTO_GET;

public class GETOrderMsgResp {

    private BasedDTO_GET dtoOrdine;
    private String msg;


    public void setDtoOrdine(BasedDTO_GET dtoOrdine) {
        this.dtoOrdine = dtoOrdine;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    //costrutt
    public GETOrderMsgResp(BasedDTO_GET dtoordine, String msg) {
        setDtoOrdine(dtoordine);
        setMsg(msg);
    }

    public String getMsg() {
        return msg;
    }

    public BasedDTO_GET getDtoOrdine() {
        return dtoOrdine;
    }
}
