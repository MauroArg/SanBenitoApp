package com.app.sanbenitoapp.Model.Agenda;

public class SetAgendaBody
{
    private String iduser;
    private String token;
    private String idmedicina;
    private String fecha_aviso;
    private String intervalo_repe;
    private String estadoAgenda;

    public SetAgendaBody(String iduser, String token, String idmedicina, String fecha_aviso, String intervalo_repe, String estadoAgenda) {
        this.iduser = iduser;
        this.token = token;
        this.idmedicina = idmedicina;
        this.fecha_aviso = fecha_aviso;
        this.intervalo_repe = intervalo_repe;
        this.estadoAgenda = estadoAgenda;
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

    public String getIdmedicina() {
        return idmedicina;
    }

    public void setIdmedicina(String idmedicina) {
        this.idmedicina = idmedicina;
    }

    public String getFecha_aviso() {
        return fecha_aviso;
    }

    public void setFecha_aviso(String fecha_aviso) {
        this.fecha_aviso = fecha_aviso;
    }

    public String getIntervalo_repe() {
        return intervalo_repe;
    }

    public void setIntervalo_repe(String intervalo_repe) {
        this.intervalo_repe = intervalo_repe;
    }

    public String getEstadoAgenda() {
        return estadoAgenda;
    }

    public void setEstadoAgenda(String estadoAgenda) {
        this.estadoAgenda = estadoAgenda;
    }
}
