package com.app.sanbenitoapp.Model.Promocion;

public class Promocion
{
    private String id;
    private String nombre;
    private String descripcion;
    private String creacion;
    private String imagen;
    private String idmedicina;
    private String fecha_limite;

    public Promocion(String id, String nombre, String descripcion, String creacion, String imagen, String idmedicina, String fecha_limite) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creacion = creacion;
        this.imagen = imagen;
        this.idmedicina = idmedicina;
        this.fecha_limite = fecha_limite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCreacion() {
        return creacion;
    }

    public void setCreacion(String creacion) {
        this.creacion = creacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getIdmedicina() {
        return idmedicina;
    }

    public void setIdmedicina(String idmedicina) {
        this.idmedicina = idmedicina;
    }

    public String getFecha_limite() {
        return fecha_limite;
    }

    public void setFecha_limite(String fecha_limite) {
        this.fecha_limite = fecha_limite;
    }
}
