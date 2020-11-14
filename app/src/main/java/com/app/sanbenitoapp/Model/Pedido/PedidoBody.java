package com.app.sanbenitoapp.Model.Pedido;

public class PedidoBody
{
    private String iduser;
    private String token;

    public PedidoBody(String iduser, String token) {
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
