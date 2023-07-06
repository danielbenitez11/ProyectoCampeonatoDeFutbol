import controlador.Controlador;

import javax.naming.ldap.Control;
import java.util.Scanner;

public class IncidenciasAPP {
    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        boolean salir = false;
        Scanner sc = new Scanner(System.in);

        while (!salir) {
            mostrarMenu();
            String opc = sc.nextLine();
            switch (opc) {
                case "1":
                    // Recoger num partido
                    controlador.insertar();
                    break;
                case "2":
                    // Modificar
                    controlador.actualizar();
                    break;
                case "3":
                    // Eliminar
                    controlador.eliminar();
                    break;
                case "4":
                    // Mostrar informe de liga
                    controlador.mostrarInformeLiga();
                    break;
                case "5":
                    // Importar equipo
                    controlador.importarEquipos();
                    break;
                case "6":
                    salir = true;
                    break;
            }
        }
    }

    /**
     * Muestra el menú al usuario
     */
    private static void mostrarMenu() {
        System.out.println(
                "1. Insertar incidencias\n" +
                        "2. Modificar dado el número de incidencia\n" +
                        "3. Eliminar Incidencia\n" +
                        "4. Mostrar informe de liga\n" +
                        "5. Importar Equipos\n" +
                        "6. Salir\n" +
                        "OPCIÓN: "
        );
    }
}
