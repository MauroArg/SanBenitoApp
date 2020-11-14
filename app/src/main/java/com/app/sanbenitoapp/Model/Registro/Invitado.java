package com.app.sanbenitoapp.Model.Registro;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

@Entity(tableName = "Invitado")
public class Invitado
{
    @Expose
    @NonNull
    @PrimaryKey(autoGenerate = true)
    Integer id;

    private String iduser;
    private String token;

    public Invitado()
    {

    }

    public Invitado(@NonNull Integer id, String iduser, String token) {
        this.id = id;
        this.iduser = iduser;
        this.token = token;
    }

    public Invitado(String iduser, String token) {
        this.iduser = iduser;
        this.token = token;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
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
}
