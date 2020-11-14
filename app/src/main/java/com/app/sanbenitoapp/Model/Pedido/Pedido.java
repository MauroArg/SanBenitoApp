package com.app.sanbenitoapp.Model.Pedido;

import java.util.List;

public class Pedido
{
    private String                      idpedido;
    private String                      creacion;
    private String                      tiempo_espera;
    private String                      codigo_envio;
    private String                      latitud;
    private String                      longitud;
    private String                      idusuario_app;
    private String                      idestados_pedido;
    private String                      comentario;
    private String                      direccion;
    private String                      min_restantes;
    private List<MedicamentoSolicitado> medicinasSolicitadas;

    public Pedido(String idpedido, String creacion, String tiempo_espera, String codigo_envio, String latitud, String longitud, String idusuario_app, String idestados_pedido, String comentario, String direccion, String min_restantes, List<MedicamentoSolicitado> medicinasSolicitadas) {
        this.idpedido = idpedido;
        this.creacion = creacion;
        this.tiempo_espera = tiempo_espera;
        this.codigo_envio = codigo_envio;
        this.latitud = latitud;
        this.longitud = longitud;
        this.idusuario_app = idusuario_app;
        this.idestados_pedido = idestados_pedido;
        this.comentario = comentario;
        this.direccion = direccion;
        this.min_restantes = min_restantes;
        this.medicinasSolicitadas = medicinasSolicitadas;
    }

    public String getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(String idpedido) {
        this.idpedido = idpedido;
    }

    public String getCreacion() {
        return creacion;
    }

    public void setCreacion(String creacion) {
        this.creacion = creacion;
    }

    public String getTiempo_espera() {
        return tiempo_espera;
    }

    public void setTiempo_espera(String tiempo_espera) {
        this.tiempo_espera = tiempo_espera;
    }

    public String getCodigo_envio() {
        return codigo_envio;
    }

    public void setCodigo_envio(String codigo_envio) {
        this.codigo_envio = codigo_envio;
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

    public String getIdusuario_app() {
        return idusuario_app;
    }

    public void setIdusuario_app(String idusuario_app) {
        this.idusuario_app = idusuario_app;
    }

    public String getIdestados_pedido() {
        return idestados_pedido;
    }

    public void setIdestados_pedido(String idestados_pedido) {
        this.idestados_pedido = idestados_pedido;
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

    public String getMin_restantes() {
        return min_restantes;
    }

    public void setMin_restantes(String min_restantes) {
        this.min_restantes = min_restantes;
    }

    public List<MedicamentoSolicitado> getMedicinasSolicitadas() {
        return medicinasSolicitadas;
    }

    public void setMedicinasSolicitadas(List<MedicamentoSolicitado> medicinasSolicitadas) {
        this.medicinasSolicitadas = medicinasSolicitadas;
    }
}
