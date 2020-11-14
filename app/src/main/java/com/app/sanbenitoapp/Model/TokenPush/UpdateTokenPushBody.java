package com.app.sanbenitoapp.Model.TokenPush;

public class UpdateTokenPushBody
{
    private String iduser;
    private String token;
    private String tokenPush;
    private String tipoUsuario;

    public UpdateTokenPushBody(String iduser, String token, String tokenPush, String tipoUsuario) {
        this.iduser = iduser;
        this.token = token;
        this.tokenPush = tokenPush;
        this.tipoUsuario = tipoUsuario;
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

    public String getTokenPush() {
        return tokenPush;
    }

    public void setTokenPush(String tokenPush) {
        this.tokenPush = tokenPush;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
