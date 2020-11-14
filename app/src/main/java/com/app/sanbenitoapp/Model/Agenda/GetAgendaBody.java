package com.app.sanbenitoapp.Model.Agenda;

public class GetAgendaBody
{
    private String iduser;
    private String token;

    public GetAgendaBody(String iduser, String token) {
        this.iduser = iduser;
        this.token = token;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
