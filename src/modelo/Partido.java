package modelo;

import java.util.Date;

public class Partido {
    private String codPartido;
    private String codEquipoLocal;
    private String codEquipoVisitante;
    private Date fecha;
    private String jornada;

    public Partido(String codPartido, String codEquipoLocal, String codEquipoVisitantte, Date fecha, String jornada) {
        this.codPartido = codPartido;
        this.codEquipoLocal = codEquipoLocal;
        this.codEquipoVisitante = codEquipoVisitantte;
        this.fecha = fecha;
        this.jornada = jornada;
    }

    public String getCodPartido() {
        return codPartido;
    }

    public void setCodPartido(String codPartido) {
        this.codPartido = codPartido;
    }

    public String getCodEquipoLocal() {
        return codEquipoLocal;
    }

    public void setCodEquipoLocal(String codEquipoLocal) {
        this.codEquipoLocal = codEquipoLocal;
    }

    public String getCodEquipoVisitante() {
        return codEquipoVisitante;
    }

    public void setCodEquipoVisitante(String codEquipoVisitante) {
        this.codEquipoVisitante = codEquipoVisitante;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    @Override
    public String toString() {
        return "Partido{" +
                "codPartido='" + codPartido + '\'' +
                ", codEquipoLocal='" + codEquipoLocal + '\'' +
                ", codEquipoVisitantte='" + codEquipoVisitante + '\'' +
                ", fecha=" + fecha +
                ", jornada='" + jornada + '\'' +
                '}';
    }
}
