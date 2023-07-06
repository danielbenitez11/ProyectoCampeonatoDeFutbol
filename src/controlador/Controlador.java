package controlador;

import dao.DAOClass;
import modelo.Incidencia;
import modelo.Tipo;
import utils.Herramientas;

import java.util.Scanner;

public class Controlador {
    private Scanner sc;
    private DAOClass dao;
    private Herramientas herramientas;
    public Controlador() {
        sc = new Scanner(System.in);
        dao = new DAOClass();
        herramientas = new Herramientas();
    }

    /**
     * Inserta los datos que recoge por teclado
     */
    public void insertar() {
        System.out.println("Código de partido:");
        String codPartido = sc.nextLine();
        System.out.println("Número de Incidencia:");
        String numIncidencia = sc.nextLine();
        System.out.println("Código de jugador:");
        String codJugador = sc.nextLine();

        Tipo incidencia = null;
        while (incidencia == null) {
            System.out.println("Tipo incidencia (Gol, Tarjeta Amarilla o Tarjeta Roja): ");
            incidencia = herramientas.validarTipoIncidencia(sc.nextLine());
        }

        String minuto = "";
        while (!herramientas.validadorMinutos(minuto)) {
            System.out.println("Minuto (1-99): ");
            minuto = sc.nextLine();
        }

        if(dao.insertarIncidencia(new Incidencia(numIncidencia,codPartido, codJugador, Integer.parseInt(minuto), incidencia))) {
            System.out.println("INSERT COMPLETADO");
        } else {
            System.err.println("ERROR AL INSERTAR LOS DATOS");
        }
    }

    /**
     * Actualiza los datos en base a los datos recogidos por teclado
     */
    public void actualizar() {
        System.out.println("Dame el número de incidencia:");
        String numIncidencia = sc.nextLine();
        System.out.println("Nuevo código de partido:");
        String codPartido = sc.nextLine();
        System.out.println("Nuevo código del jugador:");
        String codJugador = sc.nextLine();

        Tipo incidencia = null;
        while (incidencia == null) {
            System.out.println("Nuevo tipo incidencia (Gol, Tarjeta Amarilla o Tarjeta Roja):");
            incidencia = herramientas.validarTipoIncidencia(sc.nextLine());
        }

        String minuto = "";
        while (!herramientas.validadorMinutos(minuto)) {
            System.out.println("Nuevo minuto (1-99):");
            minuto = sc.nextLine();
        }

        if (dao.modificarIncidencia(new Incidencia(numIncidencia, codPartido, codJugador, Integer.parseInt(minuto), incidencia))) {
            System.out.println("INCIDENCIA MODIFICADA");
        } else {
            System.err.println("NO SE PUDO MODIFICAR LA INCIDENCIA");
        }
    }

    /**
     * Elimina los datos en base a la incidencia recogida por teclado
     */
    public void eliminar() {
        System.out.println("Número de incidencia:");
        String numIncidencia = sc.nextLine();

        if(dao.eliminarIncidencia(numIncidencia)) {
            System.out.println("INCIDENCIA ELIMINADA");
        } else {
            System.err.println("NO SE PUDO ELIMINAR LA INCIDENCIA");
        }
    }

    public void mostrarInformeLiga() {
        if (!dao.mostrarInformeLiga()) {
            System.err.println("NO SE PUDO MOSTRAR EL INFORME DE LA LIGA");
        }
    }

    /**
     * Importa los equipos en base a un fichero de datos recogido por teclado
     */
    public void importarEquipos() {
        System.out.println("Dame la ruta del fichero:");
        String rutaFichero = sc.nextLine();

        if (dao.importarEquipos(rutaFichero)) {
            System.out.println("FICHERO IMPORTADO CON ÉXITO");
        } else {
            System.err.println("NO SE PUDIERON IMPORTAR LOS DATOS");
        }
    }
}
