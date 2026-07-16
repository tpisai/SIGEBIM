package modelo.prestamo;

import java.time.LocalDate;
import modelo.enums.EstadoPrestamo;

public class SolicitudPrestamo {
    private String idSolicitud;
    private String isbnLibro;
    private String usernameUsuario;
    private LocalDate fechaSolicitud;
    private EstadoPrestamo estado;

    public SolicitudPrestamo() {
        this.fechaSolicitud = LocalDate.now();
        this.estado = EstadoPrestamo.PENDIENTE;
    }

    public SolicitudPrestamo(String idSolicitud, String isbnLibro, String usernameUsuario) {
        this.idSolicitud = idSolicitud;
        this.isbnLibro = isbnLibro;
        this.usernameUsuario = usernameUsuario;
        this.fechaSolicitud = LocalDate.now();
        this.estado = EstadoPrestamo.PENDIENTE;
    }

    public void marcarAtendida() {
        this.estado = EstadoPrestamo.ACTIVO;
    }

    public void marcarRechazada() {
        this.estado = EstadoPrestamo.RECHAZADO;
    }
    //geters y setters
    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getIsbnLibro() {
        return isbnLibro;
    }

    public void setIsbnLibro(String isbnLibro) {
        this.isbnLibro = isbnLibro;
    }

    public String getUsernameUsuario() {
        return usernameUsuario;
    }

    public void setUsernameUsuario(String usernameUsuario) {
        this.usernameUsuario = usernameUsuario;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return idSolicitud + " | Libro: " + isbnLibro + " | Usuario: " + usernameUsuario + " | " + estado;
    }
}
