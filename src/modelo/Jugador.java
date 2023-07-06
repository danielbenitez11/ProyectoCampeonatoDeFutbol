package modelo;

public class Jugador {
    private String codJugador;
    private String nombre;
    private String fechaNacimiento;
    private Demarcacion demarcacion;
    private String codEquipo;

    public Jugador(String codJugador, String nombre, String fechaNacimiento, Demarcacion demarcacion, String codEquipo) {
        this.codJugador = codJugador;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.demarcacion = demarcacion;
        this.codEquipo = codEquipo;
    }

    public String getCodJugador() {
        return codJugador;
    }

    public void setCodJugador(String codJugador) {
        this.codJugador = codJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Demarcacion getDemarcacion() {
        return demarcacion;
    }

    public void setDemarcacion(Demarcacion demarcacion) {
        this.demarcacion = demarcacion;
    }

    public String getCodEquipo() {
        return codEquipo;
    }

    public void setCodEquipo(String codEquipo) {
        this.codEquipo = codEquipo;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "codJugador='" + codJugador + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", demarcacion='" + demarcacion + '\'' +
                ", codEquipo='" + codEquipo + '\'' +
                '}';
    }
}
