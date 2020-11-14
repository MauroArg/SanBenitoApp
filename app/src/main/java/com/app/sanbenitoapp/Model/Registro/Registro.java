package com.app.sanbenitoapp.Model.Registro;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.util.List;

@Entity(tableName = "registro")
public class Registro {

    @Expose
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String token;

    @Expose
    private String tokenP;


    @Ignore
    private List<User> userData;

    public Registro(String token) {
        this.token = token;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<User> getUserData() {
        return userData;
    }

    public void setUserData(List<User> userData) {
        this.userData = userData;
    }

    public String getTokenP() {
        return tokenP;
    }

    public void setTokenP(String tokenP) {
        this.tokenP = tokenP;
    }
}
