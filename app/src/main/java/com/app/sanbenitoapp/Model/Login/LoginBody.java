package com.app.sanbenitoapp.Model.Login;

public class LoginBody
{
    private String correo;
    private String pass;

    public LoginBody(String correo, String pass) {
        this.correo = correo;
        this.pass = pass;
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
}
