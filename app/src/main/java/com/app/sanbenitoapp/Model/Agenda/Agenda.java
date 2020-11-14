package com.app.sanbenitoapp.Model.Agenda;

public class Agenda
{
    private String idagenda;
    private String intervalo_repe;
    private String iduser;
    private String idmedicina;
    private String estadoAgenda;
    private String fecha_aviso;
    private String nombreMedicina;
    private String imagenMedicina;

    public Agenda(String idagenda, String intervalo_repe, String iduser, String idmedicina, String estadoAgenda, String fecha_aviso, String nombreMedicina, String imagenMedicina) {
        this.idagenda = idagenda;
        this.intervalo_repe = intervalo_repe;
        this.iduser = iduser;
        this.idmedicina = idmedicina;
        this.estadoAgenda = estadoAgenda;
        this.fecha_aviso = fecha_aviso;
        this.nombreMedicina = nombreMedicina;
        this.imagenMedicina = imagenMedicina;
    }

    public String getIdagenda() {
        return idagenda;
    }

    public void setIdagenda(String idagenda) {
        this.idagenda = idagenda;
    }

    public String getIntervalo_repe() {
        return intervalo_repe;
    }

    public void setIntervalo_repe(String intervalo_repe) {
        this.intervalo_repe = intervalo_repe;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getIdmedicina() {
        return idmedicina;
    }

    public void setIdmedicina(String idmedicina) {
        this.idmedicina = idmedicina;
    }

    public String getEstadoAgenda() {
        return estadoAgenda;
    }

    public void setEstadoAgenda(String estadoAgenda) {
        this.estadoAgenda = estadoAgenda;
    }

    public String getFecha_aviso() {
        return fecha_aviso;
    }

    public void setFecha_aviso(String fecha_aviso) {
        this.fecha_aviso = fecha_aviso;
    }

    public String getNombreMedicina() {
        return nombreMedicina;
    }

    public void setNombreMedicina(String nombreMedicina) {
        this.nombreMedicina = nombreMedicina;
    }

    public String getImagenMedicina() {
        return imagenMedicina;
    }

    public void setImagenMedicina(String imagenMedicina) {
        this.imagenMedicina = imagenMedicina;
    }
}
