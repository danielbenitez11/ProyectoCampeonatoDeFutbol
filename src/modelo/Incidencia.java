package modelo;

public class Incidencia {

    private String numIncidencia;
    private String codPartido;
    private String codJugador;
    private int minuto;
    private Tipo tipoIncidencia;

    public Incidencia(String numIncidencia, String codPartido, String codJugador, int minuto, Tipo tipoIncidencia) {
        this.numIncidencia = numIncidencia;
        this.codPartido = codPartido;
        this.codJugador = codJugador;
        this.minuto = minuto;
        this.tipoIncidencia = tipoIncidencia;
    }

    public String getNumIncidencia() {
        return numIncidencia;
    }

    public void setNumIncidencia(String numIncidencia) {
        this.numIncidencia = numIncidencia;
    }

    public String getCodPartido() {
        return codPartido;
    }

    public void setCodPartido(String codPartido) {
        this.codPartido = codPartido;
    }

    public String getCodJugador() {
        return codJugador;
    }

    public void setCodJugador(String codJugador) {
        this.codJugador = codJugador;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public Tipo getTipoIncidencia() {
        return tipoIncidencia;
    }

    public void setTipoIncidencia(Tipo tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "num_incidencia=" + numIncidencia +
                ", cod_partido='" + codPartido + '\'' +
                ", cod_jugador='" + codJugador + '\'' +
                ", minuto=" + minuto +
                ", tipo=" + tipoIncidencia +
                '}';
    }
}
