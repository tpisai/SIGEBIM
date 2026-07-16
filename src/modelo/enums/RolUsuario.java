package modelo.enums;

public enum RolUsuario {
    //Indicar para logica
    ADMINISTRADOR("Administrador"),
    BIBLIOTECARIO("Bibliotecario"),
    LECTOR("Lector");

    private final String texto;

    RolUsuario(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
    //Para que sea usado sin necesidad de crear un object
    public static RolUsuario desdeTexto(String texto) {
        for (RolUsuario rol : values()) {
            if (rol.texto.equalsIgnoreCase(texto)) {
                return rol;
            }
        }
        //Si no reconoce que es admin o bibliotecario, lo coloca como lector por defecto
        return LECTOR;
    }
}
