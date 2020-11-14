package com.app.sanbenitoapp.Model.Pedido;

import java.util.List;

public class PedidoResponse
{
    private int          errorCode;
    private String       errorMessage;
    private List<Pedido> msg;

    public PedidoResponse(int errorCode, String errorMessage, List<Pedido> msg) {
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

    public List<Pedido> getMsg() {
        return msg;
    }

    public void setMsg(List<Pedido> msg) {
        this.msg = msg;
    }
}
