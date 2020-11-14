package com.app.sanbenitoapp.Model.Producto;

import java.util.List;

public class ProductoResponse
{
    private int               errorCode;
    private String            errorMessage;
    private List<Producto> msg;

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

    public List<Producto> getMsg() {
        return msg;
    }

    public void setMsg(List<Producto> msg) {
        this.msg = msg;
    }
}
