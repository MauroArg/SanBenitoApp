package com.app.sanbenitoapp.Model.Agenda;

public class DeleteAgendaBody
{
    String iduser;
    String token;
    String idagenda;

    public DeleteAgendaBody(String iduser, String token, String idagenda) {
        this.iduser = iduser;
        this.token = token;
        this.idagenda = idagenda;
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

    public String getIdagenda() {
        return idagenda;
    }

    public void setIdagenda(String idagenda) {
        this.idagenda = idagenda;
    }
}
