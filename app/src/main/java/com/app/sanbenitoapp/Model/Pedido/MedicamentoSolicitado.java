package com.app.sanbenitoapp.Model.Pedido;

public class MedicamentoSolicitado
{
    private String idmedicina;
    private String nombre;
    private String descripcion;
    private String nombreEstadoMedicina;
    private String imagen;
    private String dosis;
    private String cantidad;
    private String cantidad_unidad;
    private String precio;
    private String precio_promo;

    public MedicamentoSolicitado(String idmedicina, String nombre, String descripcion, String nombreEstadoMedicina, String imagen, String dosis, String cantidad, String cantidad_unidad, String precio, String precio_promo) {
        this.idmedicina = idmedicina;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nombreEstadoMedicina = nombreEstadoMedicina;
        this.imagen = imagen;
        this.dosis = dosis;
        this.cantidad = cantidad;
        this.cantidad_unidad = cantidad_unidad;
        this.precio = precio;
        this.precio_promo = precio_promo;
    }

    public String getIdmedicina() {
        return idmedicina;
    }

    public void setIdmedicina(String idmedicina) {
        this.idmedicina = idmedicina;
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

    public String getNombreEstadoMedicina() {
        return nombreEstadoMedicina;
    }

    public void setNombreEstadoMedicina(String nombreEstadoMedicina) {
        this.nombreEstadoMedicina = nombreEstadoMedicina;
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

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCantidad_unidad() {
        return cantidad_unidad;
    }

    public void setCantidad_unidad(String cantidad_unidad) {
        this.cantidad_unidad = cantidad_unidad;
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
