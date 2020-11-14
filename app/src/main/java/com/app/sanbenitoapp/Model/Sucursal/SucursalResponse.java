package com.app.sanbenitoapp.Model.Sucursal;

import java.util.List;

public class SucursalResponse
{
    private int            errorCode;
    private String         errorMessage;
    private List<Sucursal> msg;

    public SucursalResponse(int errorCode, String errorMessage, List<Sucursal> msg) {
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

    public List<Sucursal> getMsg() {
        return msg;
    }

    public void setMsg(List<Sucursal> msg) {
        this.msg = msg;
    }
}
