package modelo.prestamo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import modelo.libro.Libro;
import modelo.persona.Usuario;
import modelo.enums.EstadoPrestamo;

public class Prestamo {

    private String idPrestamo;
    private Libro libro;
    private Usuario usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaLimite;
    private LocalDate fechaDevolucion;
    private EstadoPrestamo estadoPrestamo;

    public Prestamo() {
        this.estadoPrestamo = EstadoPrestamo.ACTIVO;
        this.fechaPrestamo = LocalDate.now();
        this.fechaLimite = fechaPrestamo.plusDays(7);
    }

    public Prestamo(String idPrestamo, Libro libro, Usuario usuario,
            LocalDate fechaPrestamo, LocalDate fechaLimite, LocalDate fechaDevolucion,
            EstadoPrestamo estadoPrestamo) {
        this.idPrestamo = idPrestamo;
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
        this.fechaDevolucion = fechaDevolucion;
        this.estadoPrestamo = estadoPrestamo;
    }

    public String getFechaPrestamoFormateada() {
        return (fechaPrestamo != null) ? fechaPrestamo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";
    }

    public String getFechaLimiteFormateada() {
        return (fechaLimite != null) ? fechaLimite.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";
    }

    public String getFechaDevolucionFormateada() {
        return (fechaDevolucion != null) ? fechaDevolucion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Pendiente de devolución";
    }

    public boolean estaActivo() {
        return estadoPrestamo == EstadoPrestamo.ACTIVO;
    }

    public boolean estaVencido() {
        return estaActivo() && fechaLimite != null && LocalDate.now().isAfter(fechaLimite);
    }

    public void marcarDevuelto() {
        this.estadoPrestamo = EstadoPrestamo.DEVUELTO;
    }

    public void actualizarEstadoPorFecha() {
        if (estaVencido()) {
            this.estadoPrestamo = EstadoPrestamo.VENCIDO;
        }
    }

    public String getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(String idPrestamo) { this.idPrestamo = idPrestamo; }

    public Libro getLibro() { return libro; }
    public void setLibro(Libro libro) { this.libro = libro; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public String getEstado() { return estadoPrestamo.getTexto(); }
    public void setEstado(String estado) { this.estadoPrestamo = EstadoPrestamo.desdeTexto(estado); }

    public EstadoPrestamo getEstadoPrestamo() { return estadoPrestamo; }
    public void setEstadoPrestamo(EstadoPrestamo estadoPrestamo) { this.estadoPrestamo = estadoPrestamo; }

    public LocalDate getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(LocalDate fechaLimite) { this.fechaLimite = fechaLimite; }
}