package servicios.libros;
//@author zulmi
import java.util.ArrayList;
import modelo.libro.Libro;
import modelo.enums.CriterioBusqueda;

public class LibroService {
    //Lista estatica: las categorias son valores fijos definidos por el sistema.
    /*private static final String[] CATEGORIAS = {
        "Classics",
        "History",
        "Science",
        "Mathematics",
        "Philosophy",
        "Psychology",
        "Sociology",
        "Art",
        "Children",
        "Technology",
        "Medicine",
        "Law",
        "Economics",
        "Geography",
        "Religion",
        "Politics",
        "Film",
        "Music",
        "Education",
        "Literature"
    }; Para luego guardar en otra clase o reutilizar los nombres*/
    private final ArrayList<Libro> libros;

    public LibroService(ArrayList<Libro> libros) {
        this.libros = libros;
    }
    // CRUD
    public void registrar(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("Libro nulo");
        }
        if (libro.getIsbn() !=null) {
            throw new IllegalArgumentException("ISBN ya registrado");
        }
        libros.add(libro);
    }
    public boolean actualizar(String isbn, Libro libroNuevo) {
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getIsbn().equalsIgnoreCase(isbn)) {
                libros.set(i, libroNuevo);
                return true;
            }
        }
        return false;
    }
    public boolean eliminar(String isbn) {
        return libros.removeIf(l ->
                l.getIsbn().equalsIgnoreCase(isbn));
    }
    //Busquedas
    public ArrayList<Libro> buscarGeneral(CriterioBusqueda criterio, String texto) {
        ArrayList<Libro> resultado = new ArrayList<>();
        for (Libro l : libros) {
            if (l.coincideCon(criterio, texto)) {
                resultado.add(l);
            }
        }
        return resultado;
    }
    public ArrayList<Libro> buscarPorCategoria(String categoria) {
        ArrayList<Libro> resultado = new ArrayList<>();
        for (Libro l : libros) {
            if (l.getCategoria().getNombre().toLowerCase().contains(categoria.toLowerCase())) {
                resultado.add(l);
            }
        }
        return resultado;
    }
    public ArrayList<Libro> listarDisponibles() {
        ArrayList<Libro> resultado = new ArrayList<>();
        for (Libro l : libros) {
            if (l.getStock() > 0) {
                resultado.add(l);
            }
        }
        return resultado;
    }
    public int totalLibros() {
        return libros.size();
    }
}
