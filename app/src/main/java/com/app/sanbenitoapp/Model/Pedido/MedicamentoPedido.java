package com.app.sanbenitoapp.Model.Pedido;

public class MedicamentoPedido
{
    private String idmedicina;
    private String cantidad;

    public MedicamentoPedido(String idmedicina, String cantidad) {
        this.idmedicina = idmedicina;
        this.cantidad = cantidad;
    }

    public String getIdmedicina() {
        return idmedicina;
    }

    public void setIdmedicina(String idmedicina) {
        this.idmedicina = idmedicina;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
