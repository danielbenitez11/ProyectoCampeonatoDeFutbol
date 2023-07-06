package utils;

import modelo.Demarcacion;
import modelo.Jugador;
import modelo.Tipo;

public class Herramientas {

    public Herramientas() {

    }
    /**
     * Convierte una cadena String en un objeto demarcación
     *
     * @param d la cadena que queremos que transforme
     * @return null si no coincide
     */
    public Demarcacion validarTipoDemarcacion(String d) {
        Demarcacion demarcacion = null;
        switch (d.toLowerCase().replace(" ", "")) {
            case "portero":
                demarcacion = Demarcacion.PORTERO;
                break;
            case "defensa":
                demarcacion = Demarcacion.DEFENSA;
                break;
            case "delantero":
                demarcacion = Demarcacion.DELANTERO;
                break;
            case "medio":
                demarcacion = Demarcacion.MEDIO;
                break;
        }
        return demarcacion;
    }

    /**
     * Convierte una cadena String en un objeto tipo
     *
     * @param t la cadena que queremos que transforme
     * @return null si no coincide
     */
    public Tipo validarTipoIncidencia(String t) {
        Tipo tipoIncidencia = null;

        switch (t.toLowerCase().replace(" ", "")) {
            case "gol":
                tipoIncidencia = Tipo.GOL;
                break;
            case "tarjetaamarilla":
                tipoIncidencia = Tipo.TARJETA_AMARILLA;
                break;
            case "tarjetaroja":
                tipoIncidencia = Tipo.TARJETA_ROJA;
                break;

        }

        return tipoIncidencia;
    }

    /**
     * Valida que la cadena sea un número y que este en el rango de minutos
     *
     * @param minuto el minuto a validar
     * @return true si es correcto y false si no lo es
     */
    public boolean validadorMinutos(String minuto) {
        boolean validado = true;
        try {
            int min = Integer.parseInt(minuto);
            if(min > 99 || min < 0) {
                validado = false;
            }
        } catch (Exception e) {
            validado = false;
        }
        return validado;
    }
}
