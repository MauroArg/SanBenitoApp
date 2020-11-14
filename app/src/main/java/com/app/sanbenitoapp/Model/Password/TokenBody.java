package com.app.sanbenitoapp.Model.Password;

public class TokenBody
{
    private String correo;

    public TokenBody(String correo) {
        this.correo = correo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
