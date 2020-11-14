package com.app.sanbenitoapp.Model.Chat;

public class UpdateChatStatusBody
{
    private String iduser;
    private String token;
    private String estado;

    public UpdateChatStatusBody(String iduser, String token, String estado) {
        this.iduser = iduser;
        this.token = token;
        this.estado = estado;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
