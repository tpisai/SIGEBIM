package modelo.reporte;

import modelo.reporte.Reporte;
import java.util.ArrayList;
import modelo.libro.Libro;
import modelo.enums.EstadoLibro;

public class ReporteLibros extends Reporte {
    private final ArrayList<Libro> libros;

    public ReporteLibros(ArrayList<Libro> libros) {
        super("Reporte de libros");
        this.libros = libros;
    }

    @Override
    //Resumen de existencia de los libros
    public String generarContenido() {
        int disponibles = 0;
        int noDisponibles = 0;
        for (Libro libro : libros) {
            if (libro.getEstadoLibro() == EstadoLibro.DISPONIBLE_PRESTAMO) {
                disponibles++;
            } else {
                noDisponibles++;
            }
        }
        return getEncabezado()
                + "\nTotal de libros: " + libros.size()
                + "\nDisponibles para prestamo: " + disponibles
                + "\nNo disponibles o solo local: " + noDisponibles;
    }
}
