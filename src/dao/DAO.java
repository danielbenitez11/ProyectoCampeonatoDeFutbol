package dao;

import modelo.Incidencia;
import modelo.Jugador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DAO {
    boolean insertarIncidencia(Incidencia incidencia);
    boolean modificarIncidencia(Incidencia incidencia);
    boolean eliminarIncidencia(String numIncidencia);
    Incidencia obtenerIncidencia(String numIncidencia, Connection c);
    Jugador obtenerJugador(String codJugador);
    List<Incidencia> obtenerTodasIncidencias(Connection c);
    boolean mostrarInformeLiga();
    boolean importarEquipos(String rutaFichero);
}
