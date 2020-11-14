package com.app.sanbenitoapp.Model.Registro;

public class RegistroBody
{
    private String nombre;
    private String apellido;
    private String correo;
    private String pass;
    private String token_push;
    private String token_facebook;
    private String telefono1;
    private String telefono2;
    private String direccion;
    private String tipo_usuario;

    public RegistroBody(String nombre, String apellido, String correo, String pass, String token_push, String token_facebook, String telefono1, String telefono2, String direccion, String tipo_usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.pass = pass;
        this.token_push = token_push;
        this.token_facebook = token_facebook;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.direccion = direccion;
        this.tipo_usuario = tipo_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getToken_push() {
        return token_push;
    }

    public void setToken_push(String token_push) {
        this.token_push = token_push;
    }

    public String getToken_facebook() {
        return token_facebook;
    }

    public void setToken_facebook(String token_facebook) {
        this.token_facebook = token_facebook;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
}
