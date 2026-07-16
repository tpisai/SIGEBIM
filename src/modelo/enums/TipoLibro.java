
package modelo.enums;

/**
 *
 * @author zulmi
 */
public enum TipoLibro {
    FISICO("Fisico"),
    DIGITAL("Digital");

    private final String texto;

    TipoLibro(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public static TipoLibro desdeTexto(String texto) {
        for (TipoLibro tipo : values()) {
            if (tipo.texto.equalsIgnoreCase(texto)) {
                return tipo;
            }
        }
        return FISICO; // por defecto
    }
}
