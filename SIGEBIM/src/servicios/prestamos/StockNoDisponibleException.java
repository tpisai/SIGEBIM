package servicios.prestamos;

import modelo.libro.Libro;

public class StockNoDisponibleException extends RuntimeException {

    private final Libro libro;

    public StockNoDisponibleException(Libro libro) {
        super("El libro '" + libro.getTitulo() + "' no cuenta con stock disponible para prestamo.");
        this.libro = libro;
    }

    public Libro getLibro() {
        return libro;
    }
}