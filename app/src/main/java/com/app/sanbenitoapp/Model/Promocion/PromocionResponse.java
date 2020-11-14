package com.app.sanbenitoapp.Model.Promocion;

import java.util.List;

public class PromocionResponse
{
    private int             errorCode;
    private String          errorMessage;
    private List<Promocion> msg;

    public PromocionResponse(int errorCode, String errorMessage, List<Promocion> msg) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Promocion> getMsg() {
        return msg;
    }

    public void setMsg(List<Promocion> msg) {
        this.msg = msg;
    }
}
