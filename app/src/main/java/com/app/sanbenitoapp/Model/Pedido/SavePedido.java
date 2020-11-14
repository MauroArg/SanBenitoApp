package com.app.sanbenitoapp.Model.Pedido;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "pedido")
public class SavePedido
{
    @NonNull
    @PrimaryKey
    private String idpedido;
    private String creacion;
    private String tiempo_espera;
    private String codigo_envio;
    private String latitud;
    private String longitud;
    private String idusuario_app;
    private String idestados_pedido;
    private String comentario;

    @Ignore
    private List<MedicamentoSolicitado> medicinasSolicitadas;

    public SavePedido(@NonNull String idpedido, String creacion, String tiempo_espera, String codigo_envio, String latitud, String longitud, String idusuario_app, String idestados_pedido, String comentario, List<MedicamentoSolicitado> medicinasSolicitadas) {
        this.idpedido = idpedido;
        this.creacion = creacion;
        this.tiempo_espera = tiempo_espera;
        this.codigo_envio = codigo_envio;
        this.latitud = latitud;
        this.longitud = longitud;
        this.idusuario_app = idusuario_app;
        this.idestados_pedido = idestados_pedido;
        this.comentario = comentario;
        this.medicinasSolicitadas = medicinasSolicitadas;
    }

    public SavePedido() {
    }

    @NonNull
    public String getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(@NonNull String idpedido) {
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

    public List<MedicamentoSolicitado> getMedicinasSolicitadas() {
        return medicinasSolicitadas;
    }

    public void setMedicinasSolicitadas(List<MedicamentoSolicitado> medicinasSolicitadas) {
        this.medicinasSolicitadas = medicinasSolicitadas;
    }
}
