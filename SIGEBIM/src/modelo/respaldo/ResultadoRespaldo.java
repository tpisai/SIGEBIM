/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.respaldo;

/**
 *
 * @author zulmi
 */
import java.nio.file.Path;

public class ResultadoRespaldo {
    private final boolean exitoso;
    private final String mensaje;
    private final Path ruta;

    public ResultadoRespaldo(boolean exitoso, String mensaje, Path ruta) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.ruta = ruta;
    }

    public boolean isExitoso() {
        return exitoso;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Path getRuta() {
        return ruta;
    }
}