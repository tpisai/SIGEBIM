package servicios.prestamos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import modelo.libro.Libro;
import modelo.enums.EstadoPrestamo;
import modelo.prestamo.ColaDeEspera;
import modelo.prestamo.Prestamo;
import modelo.prestamo.SolicitudPrestamo;
import modelo.persona.Usuario;

public class PrestamoService {

    private static final String PREFIJO_ID = "TCK-";
    private static final int DIGITOS_ID = 4;

    private final ArrayList<Prestamo> prestamos;
    private final Queue<SolicitudPrestamo> colaSolicitudes;

    public PrestamoService(ArrayList<Prestamo> prestamos) {
        this.prestamos = prestamos;
        this.colaSolicitudes = new LinkedList<>();
    }

    // --- Cola de solicitudes ---
    public void agregarSolicitud(SolicitudPrestamo solicitud) {
        colaSolicitudes.add(solicitud);
    }

    public SolicitudPrestamo verSiguienteSolicitud() {
        return colaSolicitudes.peek();
    }

    public SolicitudPrestamo atenderSiguienteSolicitud() {
        return colaSolicitudes.poll();
    }

    public int cantidadSolicitudesPendientes() {
        return colaSolicitudes.size();
    }

    public Queue<SolicitudPrestamo> getColaSolicitudes() {
        return colaSolicitudes;
    }

    // --- Registro de préstamos ---
    public void registrar(Prestamo prestamo) {
        if (prestamo == null || existePrestamoActivo(prestamo.getLibro().getIsbn(), prestamo.getUsuario().getUsername())) {
            throw new IllegalArgumentException("No se puede registrar el préstamo.");
        }
        prestamos.add(prestamo);
    }

    private String generarSiguienteId() {
        int maxNumero = 0;
        for (Prestamo p : prestamos) {
            String id = p.getIdPrestamo();
            if (id != null && id.startsWith(PREFIJO_ID)) {
                try {
                    int numero = Integer.parseInt(id.substring(PREFIJO_ID.length()));
                    if (numero > maxNumero) {
                        maxNumero = numero;
                    }
                } catch (NumberFormatException e) {
                    // Ignorar IDs con formato distinto
                }
            }
        }
        return String.format(PREFIJO_ID + "%0" + DIGITOS_ID + "d", maxNumero + 1);
    }

    public Prestamo crearPrestamo(Libro libro, Usuario usuario, int diasPrestamo) {
        if (libro == null || usuario == null) {
            throw new IllegalArgumentException("Debe seleccionar libro y usuario.");
        }
        if (!usuario.isActivo()) {
            throw new IllegalStateException("Usuario inactivo");
        }
        if (existePrestamoActivo(libro.getIsbn(), usuario.getUsername())) {
            throw new IllegalStateException("Ya tiene préstamo activo");
        }
        if (!libro.puedePrestarse()) {
            throw new StockNoDisponibleException(libro);
        }

        libro.registrarPrestamo();
        Prestamo prestamo = new Prestamo(
                generarSiguienteId(),
                libro,
                usuario,
                LocalDate.now(),
                LocalDate.now().plusDays(diasPrestamo),
                null,
                EstadoPrestamo.ACTIVO
        );
        registrar(prestamo);
        return prestamo;
    }

    // --- Lista de espera ---
    public void agregarAListaEspera(Libro libro, Usuario usuario) {
        if (libro == null || usuario == null) {
            throw new IllegalArgumentException("Debe seleccionar libro y usuario.");
        }
        ColaDeEspera.agregarUsuario(libro.getIsbn(), usuario);
    }

    public boolean tieneListaEspera(String isbn) {
        return ColaDeEspera.tieneCola(isbn);
    }

    public Usuario atenderSiguienteEnEspera(String isbn) {
        return ColaDeEspera.atenderSiguiente(isbn);
    }

    // --- Aprobar solicitud ---
    public Prestamo aprobarSiguienteSolicitud(Libro libro, Usuario usuario, int diasPrestamo) {
        SolicitudPrestamo solicitud = atenderSiguienteSolicitud();
        if (solicitud == null) {
            throw new IllegalStateException("No hay solicitudes pendientes en la cola.");
        }
        if (libro == null || usuario == null) {
            solicitud.marcarRechazada();
            throw new IllegalArgumentException("Libro o usuario no encontrado.");
        }
        Prestamo prestamo = crearPrestamo(libro, usuario, diasPrestamo);
        solicitud.marcarAtendida();
        return prestamo;
    }

    // --- Devolver préstamo ---
    public boolean devolverPrestamo(String idPrestamo, Libro libro) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getIdPrestamo().equalsIgnoreCase(idPrestamo) && prestamo.estaActivo()) {
                prestamo.marcarDevuelto();
                prestamo.setFechaDevolucion(LocalDate.now()); // Captura fecha actual
                if (libro != null) {
                    libro.registrarDevolucion();
                }
                return true;
            }
        }
        return false;
    }

    // --- CRUD ---
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

    // --- Búsquedas ---
    public Prestamo buscarPrestamo(String idPrestamo) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getIdPrestamo().equalsIgnoreCase(idPrestamo)) {
                return prestamo;
            }
        }
        return null;
    }

    public Libro buscarLibroPorIsbn(ArrayList<Libro> libros, String isbn) {
        for (Libro l : libros) {
            if (l.getIsbn().equalsIgnoreCase(isbn)) return l;
        }
        return null;
    }

    public Usuario buscarUsuarioPorDni(ArrayList<Usuario> usuarios, String dni) {
        for (Usuario u : usuarios) {
            if (u.getDni().equalsIgnoreCase(dni)) return u;
        }
        return null;
    }

    public Usuario buscarLectorPorDni(ArrayList<Usuario> usuarios, String dni) {
        for (Usuario u : usuarios) {
            if (u.getDni().equalsIgnoreCase(dni) && u.esLector()) {
                return u;
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
