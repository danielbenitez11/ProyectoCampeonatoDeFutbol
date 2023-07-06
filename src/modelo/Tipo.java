package modelo;

public enum Tipo {
    GOL, TARJETA_AMARILLA, TARJETA_ROJA;

    @Override
    public String toString() {
        String cadena = "";
        switch (this) {
            case GOL:
                cadena = "GOL";
                break;
            case TARJETA_AMARILLA:
                cadena = "TARJETA AMARILLA";
                break;
            case TARJETA_ROJA:
                cadena = "TARJETA ROJA";
                break;
        }
        return cadena;
    }
}
