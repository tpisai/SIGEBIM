package modelo.enums;

public enum EstadoLibro {
    DISPONIBLE_PRESTAMO("Disponible a Prestamo"),
    STOCK_MINIMO_LOCAL("Stock minimo para Local"),
    NO_DISPONIBLE("No Disponible");

    private final String texto;

    EstadoLibro(String texto) {
        this.texto = texto;
    }
    
    //Logica que estaba en form ahora ya nop, desde aqui mas sencillo y ordenado
    public static EstadoLibro desdeStock(int stock) {
        if (stock > 2) {
            return DISPONIBLE_PRESTAMO;
        }
        if (stock > 0) {
            return STOCK_MINIMO_LOCAL;
        }
        return NO_DISPONIBLE;
    }

    public static EstadoLibro desdeTexto(String texto) {
        for (EstadoLibro estado : values()) {
            if (estado.texto.equalsIgnoreCase(texto)) {
                return estado;
            }
        }
        return NO_DISPONIBLE;
    }
    //getter, sin setter al ser un private final*
    public String getTexto() {
        return texto;
    }
}
