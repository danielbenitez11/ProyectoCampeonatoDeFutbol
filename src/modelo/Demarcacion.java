package modelo;

public enum Demarcacion {
    PORTERO, DEFENSA, DELANTERO, MEDIO;

    @Override
    public String toString() {
        String cadena = "";
        switch (this) {
            case PORTERO:
                cadena = "PORTERO";
                break;
            case DEFENSA:
                cadena = "DEFENSA";
                break;
            case DELANTERO:
                cadena = "DELANTERO";
                break;
            case MEDIO:
                cadena = "MEDIO";
                break;
        }
        return cadena;
    }
}
