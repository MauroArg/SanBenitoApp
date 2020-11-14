package com.app.sanbenitoapp.Model.Pedido;

public class DetallePedidoBody
{
    private String iduser;
    private String token;
    private String idpedido;

    public DetallePedidoBody(String iduser, String token, String idpedido) {
        this.iduser = iduser;
        this.token = token;
        this.idpedido = idpedido;
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

    public String getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(String idpedido) {
        this.idpedido = idpedido;
    }
}
