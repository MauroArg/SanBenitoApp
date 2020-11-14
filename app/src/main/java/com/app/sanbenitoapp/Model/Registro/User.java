package com.app.sanbenitoapp.Model.Registro;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

@Entity(tableName = "user")
public class User {

    @NonNull
    @PrimaryKey
    String idusuario_app;
    String nombre;
    String apellido;
    String correo;
    String token_push;
    String token_facebook;
    String telefono1;
    String telefono2;
    String direccion;

    @Expose
    String tipo_usuario;

    public User(String nombre, String apellido, String correo, String token_push, String token_facebook, String telefono1, String telefono2, String direccion, String tipo_usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.token_push = token_push;
        this.token_facebook = token_facebook;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.direccion = direccion;
        this.tipo_usuario = tipo_usuario;
    }

    @NonNull
    public String getIdusuario_app() {
        return idusuario_app;
    }

    public void setIdusuario_app(@NonNull String idusuario_app) {
        this.idusuario_app = idusuario_app;
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
