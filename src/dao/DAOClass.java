package dao;

import modelo.Demarcacion;
import modelo.Incidencia;
import modelo.Jugador;
import modelo.Tipo;
import utils.Conexion;
import utils.Herramientas;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOClass implements DAO {
    /**
     * Usa la librería de MySQL conector para insertar la nueva incidencia en la BD
     *
     * @param incidencia la nueva incidencia
     * @return true si la operación se realizó con éxito y false si no pudo completarla
     */
    @Override
    public boolean insertarIncidencia(Incidencia incidencia) {
        boolean exitoInsert = false;
        try {
            Connection c = null;
            Tipo tipoIncidencia = incidencia.getTipoIncidencia();
            String codJugador = incidencia.getCodJugador();
            try {
                if (!(obtenerJugador(codJugador).getDemarcacion() == Demarcacion.PORTERO && tipoIncidencia == Tipo.GOL)) {
                    String sql = "INSERT INTO INCIDENCIA VALUES (?,?,?,?,?)";
                    // TRANSACCIÓN:
                    // OBTENEMOS LA CONEXIÓN
                    c = Conexion.getConnection();
                    // DESACTIVAMOS EL AUTOCOMMIT
                    c.setAutoCommit(false);
                    // MOSTRAMOS LOS DATOS
                    for (Incidencia i : obtenerTodasIncidencias(c)) {
                        System.out.println(i + "\n");
                    }
                    // INSERTAMOS LA NUEVA INCIDENCIA
                    PreparedStatement consultaSql = c.prepareStatement(sql);
                    consultaSql.setString(1, incidencia.getNumIncidencia());
                    consultaSql.setString(2, incidencia.getCodPartido());
                    consultaSql.setString(3, codJugador);
                    consultaSql.setInt(4, incidencia.getMinuto());
                    consultaSql.setString(5, tipoIncidencia.toString());

                    if (consultaSql.executeUpdate() == 1) {
                        // EJECUTAMOS EL COMMIT
                        c.commit();
                        exitoInsert = true;
                    } else {
                        // EJECUTAMOS EL ROLLBACK
                        c.rollback();
                    }

                    c.close();
                } else {
                    System.err.println("LOS JUGADORES PORTEROS NO PUEDEN TENER INCIDENCIAS TIPO GOL");
                }
            } catch (IOException e) {
                System.err.println("ERROR AL OBTENER CONEXIÓN CON LA BD");
                c.close();
            } catch (SQLException e) {
                System.err.println("ERROR EN LA OPERACIÓN (INSERT)");
                c.close();
            }
        } catch (Exception e) {
            System.err.println("ERROR GENERAL");
        }
        return exitoInsert;
    }

    /**
     * Usa la librería de MySQL conector para modificar la incidencia con el mismo número
     *
     * @param incidencia la incidencia que contiene los nuevos datos
     * @return true si completó la actualización y false si no pudo hacerla
     */
    @Override
    public boolean modificarIncidencia(Incidencia incidencia) {
        boolean exitoUpdate = false;
        try {
            Tipo tipoIncidencia = incidencia.getTipoIncidencia();
            String codJugador = incidencia.getCodJugador();
            Connection c = null;
            try {
                if (!(obtenerJugador(codJugador).getDemarcacion() == Demarcacion.PORTERO && tipoIncidencia == Tipo.GOL)) {
                    String sql = "UPDATE INCIDENCIA SET cod_partido=?, cod_jugador=?, minuto=?, tipo=? WHERE num_incidencia=?";
                    // TRANSACCIÓN
                    // OBTENERMOS LA CONEXIÓN
                    c = Conexion.getConnection();
                    // DESACTIVAMOS EL AUTOCOMMIT
                    c.setAutoCommit(false);
                    // MOSTRAMOS LOS DATOS CON UN SELECT
                    for (Incidencia i : obtenerTodasIncidencias(c)) {
                        System.out.println(i + "\n");
                    }
                    // ACTUALIZAMOS LA INCIDENCIA
                    PreparedStatement consultaSql = c.prepareStatement(sql);
                    consultaSql.setString(1, incidencia.getCodPartido());
                    consultaSql.setString(2, codJugador);
                    consultaSql.setInt(3, incidencia.getMinuto());
                    consultaSql.setString(4, tipoIncidencia.toString().replace(" ", ""));
                    consultaSql.setString(5, incidencia.getNumIncidencia());

                    if (consultaSql.executeUpdate() == 1) {
                        // EJECUTAMOS EL COMMIT
                        c.commit();
                        exitoUpdate = true;
                    } else {
                        // EJECUTAMOS EL ROLLBACK
                        c.rollback();
                    }
                    c.close();
                } else {
                    System.err.println("LOS JUGADORES PORTEROS NO PUEDEN TENER INCIDENCIAS TIPO GOL");
                }
            } catch (IOException e) {
                System.err.println("ERROR AL OBTENER CONEXIÓN CON LA BD");
                c.close();
            } catch (SQLException ex) {
                System.err.println("ERROR EN LA OPERACIÓN (UPDATE)");
                c.close();
            }
        } catch (Exception e) {
            System.err.println("ERROR GENERAL");
        }
        return exitoUpdate;
    }

    /**
     * Elimina la incidencia que tenga el mismo número de incidencia
     *
     * @param numIncidencia el número de la incidencia que se va a eliminar
     * @return true si completó la eliminación y false si no pudo hacerlo
     */
    @Override
    public boolean eliminarIncidencia(String numIncidencia) {
        boolean exitoDelete = false;
        try {
            Connection c = null;
            String sql = "DELETE FROM INCIDENCIA WHERE num_incidencia=?";
            // TRANSACCIÓN
            try {
                // OBTENERMOS LA CONEXIÓN
                c = Conexion.getConnection();
                // DESACTIVAMOS EL AUTOCOMMIT
                c.setAutoCommit(false);
                // MOSTRAMOS LOS DATOS CON UN SELECT
                for (Incidencia i : obtenerTodasIncidencias(c)) {
                    System.out.println(i + "\n");
                }
                // ELIMINAMOS LA INCIDENCIA
                PreparedStatement consultaSql = c.prepareStatement(sql);
                consultaSql.setString(1, numIncidencia);

                if (consultaSql.executeUpdate() == 1) {
                    // EJECUTAMOS EL COMMIT
                    c.commit();
                    exitoDelete = true;
                } else {
                    // EJECUTAMOS EL ROLLBACK
                    c.rollback();
                }
                c.close();
            } catch (IOException e) {
                System.err.println("ERROR AL OBTENER CONEXIÓN CON LA BD");
                c.close();
            } catch (SQLException e) {
                System.err.println("ERROR EN LA OPERACIÓN (DELETE)");
                c.close();
            }
        } catch (Exception e) {
            System.err.println("ERROR GENERAL");
        }
        return exitoDelete;
    }

    /**
     * Importa equipos provenientes de un fichero .csv o .txt con el separador ;
     *
     * @param rutaFichero la ruta del fichero que contiene los datos
     * @return true si completó la inserción y false si no pudo hacerlo
     */
    @Override
    public boolean importarEquipos(String rutaFichero) {
        boolean exitoImportarDatos = false;
        try {
            Connection c = null;
            File f = new File(rutaFichero);
            if (f.exists() && rutaFichero.endsWith(".csv") || rutaFichero.endsWith(".txt")) {
                try {
                    BufferedReader leer = new BufferedReader(new FileReader(f));
                    String linea = leer.readLine();
                    String sql = "INSERT INTO EQUIPO VALUES ";
                    int contLineas = 0;

                    while (linea != null) {
                        String[] datos = linea.split(";");
                        sql += "('" + datos[0] + "','" + datos[1] + "','" + datos[2] + "'),";
                        contLineas++;
                        linea = leer.readLine();
                    }
                    sql = sql.substring(0, sql.length() - 1);
                    // TRANSACCCIÓN:
                    // OBTENEMOS LA CONEXIÓN
                    c = Conexion.getConnection();
                    // DESACTIVAMOS EL AUTOCOMMIT
                    c.setAutoCommit(false);
                    // MOSTRAMOS LOS DATOS
                    for (Incidencia i : obtenerTodasIncidencias(c)) {
                        System.out.println(i + "\n");
                    }
                    // EJECUTAMOS LA CONSULTA
                    PreparedStatement consultaSql = c.prepareStatement(sql);
                    if (consultaSql.executeUpdate() == contLineas) {
                        // HACEMOS EL COMMIT
                        exitoImportarDatos = true;
                        c.commit();
                    } else {
                        // HACEMOS EL ROLLBACK
                        c.rollback();
                    }
                    c.close();
                } catch (FileNotFoundException e) {
                    System.err.println("EL FICHERO NO EXISTE");
                } catch (IOException e) {
                    System.err.println("ERROR DE CONEXIÓN A LA BD O ERROR DE E/S CON EL FICHERO");
                    c.close();
                } catch (SQLException e) {
                    System.err.println("ERROR EN LA OPERACIÓN (INSERT MÚLTIPLE)");
                    c.close();
                }
            } else {
                System.err.println("EL FICHERO DEBE ESTAR EN FORMATO .CSV O .TXT");
            }
        } catch (Exception e) {
            System.err.println("ERROR GENERAL");
        }
        return exitoImportarDatos;
    }

    /**
     * Obtiene la incidencia que tenga el mismo número de incidencia
     *
     * @param numInc el número de incidencia que se busca
     * @return true si completó la consulta y false si no pudo hacerlo
     */
    @Override
    public Incidencia obtenerIncidencia(String numInc, Connection c) {
        Incidencia incidencia = null;
        try {
            Herramientas herramientas = new Herramientas();
            boolean sinTransaccion = false;
            try {
                if (c == null) {
                    c = Conexion.getConnection();
                    sinTransaccion = true;
                }
                String sql = "SELECT * FROM INCIDENCIA WHERE num_incidencia=?";
                PreparedStatement consultaSql = c.prepareStatement(sql);
                consultaSql.setString(1, numInc);
                ResultSet set = consultaSql.executeQuery();

                while (set.next()) {
                    String numIncidencia = set.getString("num_incidencia");
                    String codPartido = set.getString("cod_partido");
                    String codJugador = set.getString("cod_jugador");
                    int minuto = set.getInt("minuto");
                    String tipoIncidencia = set.getString("tipo");
                    incidencia = new Incidencia(numIncidencia, codPartido, codJugador, minuto, herramientas.validarTipoIncidencia(tipoIncidencia));
                }

                if (sinTransaccion) {
                    c.close();
                }
            } catch (IOException e) {
                System.err.println("ERROR DE CONEXIÓN A LA BD");
                c.close();
            } catch (SQLException e) {
                System.err.println("ERROR EN LA OPERACIÓN (SELECT)");
                c.close();
            }
        } catch (Exception e) {
            System.err.println("ERROR GENERAL");
        }
        return incidencia;
    }

    /**
     * Obtiene el jugador que tenga el mismo código que le pasamos
     *
     * @param codJug código de jugador a comprobar
     * @return null si no encontró al jugador
     */
    @Override
    public Jugador obtenerJugador(String codJug) {
        Jugador jugador = null;
        try {
            Herramientas herramientas = new Herramientas();
            Connection c = null;
            try {
                c = Conexion.getConnection();
                String sql = "SELECT * FROM JUGADOR WHERE cod_jugador=?";
                PreparedStatement consultaSql = c.prepareStatement(sql);
                consultaSql.setString(1, codJug);
                ResultSet set = consultaSql.executeQuery();

                while (set.next()) {
                    String codJugador = set.getString("cod_jugador");
                    String nombre = set.getString("nombre");
                    String fechaNacimiento = set.getDate("fecha_nacimiento").toString();
                    String demarcacion = set.getString("demarcacion");
                    String codEquipo = set.getString("cod_equipo");
                    jugador = new Jugador(codJugador, nombre, fechaNacimiento, herramientas.validarTipoDemarcacion(demarcacion), codEquipo);
                }
                c.close();
            } catch (IOException e) {
                System.err.println("ERROR DE CONEXIÓN A LA BD");
                c.close();
            } catch (SQLException e) {
                System.err.println("ERROR EN LA OPERACIÓN (SELECT)");
                c.close();
            }
        } catch (Exception e) {
            System.err.println("ERROR GENERAL");
        }
        return jugador;
    }

    /**
     * Consulta para obtener todas las incidencias que existen en la BD
     *
     * @param c la conexión actual para incluirla en la misma transacción, para crear una conexión propia mandar null
     * @return una lista con todas las incidencias
     */
    @Override
    public List<Incidencia> obtenerTodasIncidencias(Connection c) {
        List<Incidencia> listIncidencia = new ArrayList<>();
        try {
            Herramientas herramientas = new Herramientas();
            String sql = "SELECT * FROM INCIDENCIA";
            try {
                boolean sinTransaccion = false;
                if (c == null) {
                    c = Conexion.getConnection();
                    sinTransaccion = true;
                }
                Statement consultaSql = c.createStatement();
                ResultSet set = consultaSql.executeQuery(sql);

                while (set.next()) {
                    String numIncidencia = set.getString("num_incidencia");
                    String codPartido = set.getString("cod_partido");
                    String codJugador = set.getString("cod_jugador");
                    int minuto = set.getInt("minuto");
                    Tipo tipoIncidencia = herramientas.validarTipoIncidencia(set.getString("tipo"));
                    listIncidencia.add(new Incidencia(numIncidencia, codPartido, codJugador, minuto, tipoIncidencia));
                }

                if (sinTransaccion) {
                    c.close();
                }
            } catch (IOException e) {
                System.err.println("ERROR DE CONEXIÓN A LA BD");
                c.close();
            } catch (SQLException e) {
                System.err.println("ERROR EN LA OPERACIÓN (SELECT)");
                c.close();
            }
        } catch (Exception e) {
            System.err.println("ERROR GENERAL");
        }
        return listIncidencia;
    }

    /**
     * Muestra el informe de liga por pantalla
     *
     * @return true si lo pudo hacer y false si no lo pudo hacer
     */
    @Override
    public boolean mostrarInformeLiga() {
        boolean exitoInforme = false;
        try {
            Connection c = null;
            String sql = "{call mostrar_informe()}";
            try {
                c = Conexion.getConnection();
                c.setAutoCommit(false);
                CallableStatement cst = c.prepareCall(sql);

                if (cst.execute()) {
                    exitoInforme = true;
                    c.commit();
                    System.out.println(cst.getString(1));
                } else {
                    c.rollback();
                    c.close();
                }
            } catch (IOException e) {
                System.err.println("ERROR DE CONEXIÓN A LA BD");
                c.close();
            } catch (SQLException e) {
                System.err.println("ERROR EN LA OPERACIÓN (SELECT)");
                c.close();
            }
        } catch (Exception e) {
            System.err.println("ERROR GENERAL");
        }
        return exitoInforme;
    }
}