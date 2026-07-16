package modelo.prestamo;

import java.time.LocalDate;
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
    //Constructores
    public Prestamo() {
        this.estadoPrestamo = EstadoPrestamo.ACTIVO;
        this.fechaPrestamo = LocalDate.now();
        this.fechaLimite = fechaPrestamo.plusDays(7);
    }

    public Prestamo(String idPrestamo, Libro libro, Usuario usuario,
            LocalDate fechaPrestamo, LocalDate fechaDevolucion, EstadoPrestamo estadoPrestamo) {
        this.idPrestamo = idPrestamo;
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estadoPrestamo = estadoPrestamo;
    }
    //Metodos
    //Logica para el form
    public boolean estaActivo() {
        return estadoPrestamo == EstadoPrestamo.ACTIVO;
    }

    public boolean estaVencido() {
        return estaActivo()
                && fechaDevolucion != null
                && LocalDate.now().isAfter(fechaDevolucion);
    }

    public void marcarDevuelto() {
        this.estadoPrestamo = EstadoPrestamo.DEVUELTO;
    }

    public void actualizarEstadoPorFecha() {
        if (estaVencido()) {
            this.estadoPrestamo = EstadoPrestamo.VENCIDO;
        }
    }
    //Getters y Setters
    public String getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getEstado() {
        return estadoPrestamo.getTexto();
    }

    public void setEstado(String estado) {
        this.estadoPrestamo = EstadoPrestamo.desdeTexto(estado);
    }

    public EstadoPrestamo getEstadoPrestamo() {
        return estadoPrestamo;
    }

    public void setEstadoPrestamo(EstadoPrestamo estadoPrestamo) {
        this.estadoPrestamo = estadoPrestamo;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
}
