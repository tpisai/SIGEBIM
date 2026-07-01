package modelo.reporte;

import modelo.reporte.Reporte;
import java.util.ArrayList;
import modelo.persona.Usuario;

public class ReporteUsuarios extends Reporte {
    private final ArrayList<Usuario> usuarios;

    public ReporteUsuarios(ArrayList<Usuario> usuarios) {
        super("Reporte de usuarios");
        this.usuarios = usuarios;
    }

    @Override
    //Reporte para ver cantidad de usuarios cuantos existentes y no existentes
    public String generarContenido() {
        int activos = 0;
        int inactivos = usuarios.size() - activos;
        for (Usuario usuario : usuarios) {
            if (usuario.isActivo()) {
                activos++;
            }
        }
        return getEncabezado()
                + "\nTotal de usuarios: " + usuarios.size()
                + "\nUsuarios activos: " + activos
                + "\nUsuarios inactivos: " + inactivos;
    }
}
