package com.app.sanbenitoapp.Model.Agenda;

import java.util.List;

public class AgendaResponse
{
    private int errorCode;
    private String errorMessage;
    private List<Agenda> msg;

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

    public List<Agenda> getMsg() {
        return msg;
    }

    public void setMsg(List<Agenda> msg) {
        this.msg = msg;
    }
}
