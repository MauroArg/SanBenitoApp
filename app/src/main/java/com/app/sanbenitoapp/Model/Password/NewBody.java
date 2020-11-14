package com.app.sanbenitoapp.Model.Password;

public class NewBody
{
    String correo;
    String pass;

    public NewBody(String correo, String pass) {
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
