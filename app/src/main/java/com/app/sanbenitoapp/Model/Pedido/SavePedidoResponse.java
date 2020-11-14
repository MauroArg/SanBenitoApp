package com.app.sanbenitoapp.Model.Pedido;

import java.util.List;

public class SavePedidoResponse
{
    private int              errorCode;
    private String           errorMessage;
    private List<SavePedido> msg;

    public SavePedidoResponse(int errorCode, String errorMessage, List<SavePedido> msg) {
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

    public List<SavePedido> getMsg() {
        return msg;
    }

    public void setMsg(List<SavePedido> msg) {
        this.msg = msg;
    }
}
