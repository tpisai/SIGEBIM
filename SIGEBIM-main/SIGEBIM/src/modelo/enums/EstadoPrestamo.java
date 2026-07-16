package modelo.enums;

public enum EstadoPrestamo {
    PENDIENTE("Pendiente"),
    ACTIVO("Activo"),
    DEVUELTO("Devuelto"),
    VENCIDO("Vencido"),
    RECHAZADO("Rechazado");

    private final String texto;

    EstadoPrestamo(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
    //La misma vaina, logica para el archivo
    public static EstadoPrestamo desdeTexto(String texto) {
        if (texto==null) return PENDIENTE;
        for (EstadoPrestamo estado : values()) {
            if (estado.name().equalsIgnoreCase(texto) || estado.texto.equalsIgnoreCase(texto)) {
                return estado;
            }
        }
        return ACTIVO;
    }
}
