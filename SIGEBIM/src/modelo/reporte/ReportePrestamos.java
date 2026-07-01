package modelo.reporte;

import modelo.reporte.Reporte;
import modelo.prestamo.Prestamo;
import java.util.ArrayList;

public class ReportePrestamos extends Reporte {
    private final ArrayList<Prestamo> prestamos;

    public ReportePrestamos(ArrayList<Prestamo> prestamos) {
        super("Reporte de prestamos");
        this.prestamos = prestamos;
    }

    @Override
    //Reporte de el estado de cada prestamo
    public String generarContenido() {
        int activos = 0;
        int vencidos = 0;
        int devueltos = 0;
        for (Prestamo prestamo : prestamos) {
            prestamo.actualizarEstadoPorFecha();
            switch (prestamo.getEstadoPrestamo()) {
                case ACTIVO -> activos++;
                case VENCIDO -> vencidos++;
                case DEVUELTO -> devueltos++;
            }
        }
        return getEncabezado()
                + "\nTotal de prestamos: " + prestamos.size()
                + "\nActivos: " + activos
                + "\nVencidos: " + vencidos
                + "\nDevueltos: " + devueltos;
    }
}
