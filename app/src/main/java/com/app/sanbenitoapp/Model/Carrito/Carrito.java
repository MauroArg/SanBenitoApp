package com.app.sanbenitoapp.Model.Carrito;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "carrito")
public class Carrito
{
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public Integer idMedi;

    private String id;
    private String nombre;
    private String descripcion;
    private String creacion;
    private String imagen;
    private String dosis;
    private String intervalo;
    private String cantidad_unidad;
    private String presentacion;
    private String precio;
    private String precio_promo;
    private int    cantidad;
    private String comentario;

    @NonNull
    public Integer getIdMedi() {
        return idMedi;
    }

    public void setIdMedi(@NonNull Integer idMedi) {
        this.idMedi = idMedi;
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

    public String getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(String intervalo) {
        this.intervalo = intervalo;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Carrito(String id, String nombre, String descripcion, String creacion, String imagen, String dosis, String intervalo, String cantidad_unidad, String presentacion, String precio, String precio_promo, int cantidad, String comentario) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creacion = creacion;
        this.imagen = imagen;
        this.dosis = dosis;
        this.intervalo = intervalo;
        this.cantidad_unidad = cantidad_unidad;
        this.presentacion = presentacion;
        this.precio = precio;
        this.precio_promo = precio_promo;
        this.cantidad = cantidad;
        this.comentario = comentario;


    }
}
