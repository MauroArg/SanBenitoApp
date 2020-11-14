package com.app.sanbenitoapp.Model.Producto;

public class Producto
{
    private String id;
    private String nombre;
    private String descripcion;
    private String creacion;
    private String imagen;
    private String dosis;
    private String cantidad_unidad;
    private String presentacion;
    private String precio;
    private String precio_promo;

    public Producto(String id, String nombre, String descripcion, String creacion, String imagen, String dosis, String cantidad_unidad, String presentacion, String precio, String precio_promo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creacion = creacion;
        this.imagen = imagen;
        this.dosis = dosis;
        this.cantidad_unidad = cantidad_unidad;
        this.presentacion = presentacion;
        this.precio = precio;
        this.precio_promo = precio_promo;
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

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getCantidad_unidad() {
        return cantidad_unidad;
    }

    public void setCantidad_unidad(String cantidad_unidad) {
        this.cantidad_unidad = cantidad_unidad;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getPrecio_promo() {
        return precio_promo;
    }

    public void setPrecio_promo(String precio_promo) {
        this.precio_promo = precio_promo;
    }
}
