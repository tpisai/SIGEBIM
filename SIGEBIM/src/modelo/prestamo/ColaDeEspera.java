package modelo.prestamo;

import java.util.*;

import modelo.persona.Usuario;

public class ColaDeEspera {
    private static final Map<String, Queue<Usuario>> colasPorLibro = new HashMap<>();

    public static void agregarUsuario(String isbn, Usuario usuario) {
        colasPorLibro.putIfAbsent(isbn, new LinkedList<>());
        colasPorLibro.get(isbn).add(usuario);
    }

    public static Queue<Usuario> obtenerCola(String isbn) {
        return colasPorLibro.getOrDefault(isbn, new LinkedList<>());
    }

    public static Usuario atenderSiguiente(String isbn) {
        Queue<Usuario> cola = colasPorLibro.get(isbn);
        return (cola != null) ? cola.poll() : null;
    }

    public static boolean tieneCola(String isbn) {
        return colasPorLibro.containsKey(isbn) && !colasPorLibro.get(isbn).isEmpty();
    }
}
