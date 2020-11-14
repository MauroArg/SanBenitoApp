package com.app.sanbenitoapp.Model.Pedido;

import java.util.List;

public class SavePedidoBody
{
    private String                  iduser;
    private String                  token;
    private String                  latitud;
    private String                  longitud;
    private String                  comentario;
    private String                  direccion;
    private List<MedicamentoPedido> meds;

    public SavePedidoBody(String iduser, String token, String latitud, String longitud, String comentario, String direccion, List<MedicamentoPedido> meds) {
        this.iduser = iduser;
        this.token = token;
        this.latitud = latitud;
        this.longitud = longitud;
        this.comentario = comentario;
        this.direccion = direccion;
        this.meds = meds;
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

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<MedicamentoPedido> getMeds() {
        return meds;
    }

    public void setMeds(List<MedicamentoPedido> meds) {
        this.meds = meds;
    }
}
