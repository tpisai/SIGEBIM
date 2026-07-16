package servicios.libros;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import modelo.libro.Libro;
import modelo.enums.EstadoPrestamo;
import modelo.prestamo.Prestamo;
import modelo.prestamo.SolicitudPrestamo;
import modelo.persona.Usuario;
import modelo.interfaces.Gestionable;

public class PrestamoService {
    private final ArrayList<Prestamo> prestamos;
    private final Queue<SolicitudPrestamo> colaSolicitudes;

    public PrestamoService(ArrayList<Prestamo> prestamos) {
        this.prestamos = prestamos;
        this.colaSolicitudes = new LinkedList<>();
    }

    //Cola FIFO: el primero que solicita el prestamo sera el primero en ser atendido.
    public void agregarSolicitud(SolicitudPrestamo solicitud) {
        colaSolicitudes.add(solicitud);
    }

    //para ver quien sigue sin removerlo.
    public SolicitudPrestamo verSiguienteSolicitud() {
        return colaSolicitudes.peek();
    }

    //Equivale a cola.poll(): remueve y retorna la primera solicitud de la cola.
    public SolicitudPrestamo atenderSiguienteSolicitud() {
        return colaSolicitudes.poll();
    }

    //Equivale a cola.size(): devuelve cuantas solicitudes estan pendientes.
    public int cantidadSolicitudesPendientes() {
        return colaSolicitudes.size();
    }

    public Queue<SolicitudPrestamo> getColaSolicitudes() {
        return colaSolicitudes;
    }

    //Registrar Prestamo
    public void registrar(Prestamo prestamo) {
        if (prestamo == null || existePrestamoActivo(prestamo.getLibro().getIsbn(), prestamo.getUsuario().getUsername())) {
            throw new IllegalArgumentException("No se puede registrar el prestamo.");
        }
        prestamos.add(prestamo);
    }

    //Valida antes de guardar en el arraylist el objeto
    public Prestamo crearPrestamo(String idPrestamo, Libro libro, Usuario usuario, int diasPrestamo) {
        //Validar para recien permitir registrar el prestamo al usuario
        if (libro == null || usuario == null) {
            throw new IllegalArgumentException("Debe seleccionar libro y usuario.");
        }
        if (!usuario.isActivo()) {
            throw new IllegalStateException("Usuario inactivo");
        }
        if (!libro.puedePrestarse()) {
            throw new IllegalStateException("El libro no cuenta con stock disponible para prestamo.");
        }
        if (existePrestamoActivo(libro.getIsbn(), usuario.getUsername())) {
            throw new IllegalStateException("Ya tiene préstamo activo");
        }
        libro.registrarPrestamo();
        Prestamo prestamo = new Prestamo(
                idPrestamo,
                libro,
                usuario,
                LocalDate.now(),
                LocalDate.now().plusDays(diasPrestamo), // fechaLimite
                null,                                    // fechaDevolucion (aún no devuelto)
                EstadoPrestamo.ACTIVO
        );
        registrar(prestamo);
        return prestamo;
    }

    //Atiende la primera solicitud de la cola y, si el libro tiene stock, la convierte en prestamo.
    public Prestamo aprobarSiguienteSolicitud(Libro libro, Usuario usuario, int diasPrestamo) {
        SolicitudPrestamo solicitud = atenderSiguienteSolicitud();
        if (solicitud == null) {
            throw new IllegalStateException("No hay solicitudes pendientes en la cola.");
        }
        if (libro == null || usuario == null) {
            solicitud.marcarRechazada();
            throw new IllegalArgumentException("Libro o usuario no encontrado.");
        }
        Prestamo prestamo = crearPrestamo(
                "P-" + solicitud.getIdSolicitud(),
                libro,
                usuario,
                diasPrestamo
        );
        solicitud.marcarAtendida();
        return prestamo;
    }

    //Aplica la devolucion correcta
    public boolean devolverPrestamo(String idPrestamo, Libro libro) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getIdPrestamo().equalsIgnoreCase(idPrestamo) && prestamo.estaActivo()) {
                prestamo.marcarDevuelto();
                if (libro != null) {
                    libro.registrarDevolucion();
                }
                return true;
            }
        }
        return false;
    }

    public boolean actualizar(String codigo, Prestamo prestamoActualizado) {
        for (int i = 0; i < prestamos.size(); i++) {
            if (prestamos.get(i).getIdPrestamo().equalsIgnoreCase(codigo)) {
                prestamos.set(i, prestamoActualizado);
                return true;
            }
        }
        return false;
    }

    public boolean eliminar(String codigo) {
        return prestamos.removeIf(p -> p.getIdPrestamo().equalsIgnoreCase(codigo));
    }

    //Busca un prestamo por su ID, para pantallas como Gestion Devolucion
    public Prestamo buscarPrestamo(String idPrestamo) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getIdPrestamo().equalsIgnoreCase(idPrestamo)) {
                return prestamo;
            }
        }
        return null;
    }

    public boolean existePrestamoActivo(String isbn, String username) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.estaActivo()
                    && prestamo.getLibro().getIsbn().equalsIgnoreCase(isbn)
                    && prestamo.getUsuario().getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }
}