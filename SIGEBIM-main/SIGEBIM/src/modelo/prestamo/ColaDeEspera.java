package modelo.prestamo;

import java.util.*;
import modelo.persona.Usuario;

public class ColaDeEspera {
    private static Map<String, Queue<Usuario>> colas = new HashMap<>();

    public static void agregarUsuario(String isbn, Usuario usuario) {
        colas.putIfAbsent(isbn, new LinkedList<>());
        colas.get(isbn).add(usuario);
    }

    public static Queue<Usuario> obtenerCola(String isbn) {
        return colas.getOrDefault(isbn, new LinkedList<>());
    }

    public static Usuario atenderSiguiente(String isbn) {
        Queue<Usuario> cola = colas.get(isbn);
        if (cola != null && !cola.isEmpty()) {
            return cola.poll();
        }
        return null;
    }

    public static boolean tieneCola(String isbn) {
        return colas.containsKey(isbn) && !colas.get(isbn).isEmpty();
    }
}
