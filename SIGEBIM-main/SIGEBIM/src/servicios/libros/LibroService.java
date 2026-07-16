package servicios.libros;

//@author zulmi
import archivos.ArchivoLibro;
import java.util.ArrayList;
import modelo.enums.CriterioBusqueda;
import modelo.enums.TipoLibro;
import modelo.interfaces.Gestionable;
import modelo.libro.Libro;

public class LibroService implements Gestionable<Libro> {

    private final ArrayList<Libro> libros;
    private final ArchivoLibro archivoLibro;
    // Constructor
    public LibroService() {
        archivoLibro = new ArchivoLibro();
        libros = archivoLibro.cargarLibros();
    }
    //CRUD
    @Override
    public void registrar(Libro libro) {
        validarLibro(libro, false, null);
        libros.add(0, libro);
        archivoLibro.guardarLibros(libros);
    }
    
    @Override
    public void actualizar(int index, Libro libro) {
        if (index < 0 || index >= libros.size()) {
            return;
        }
        String isbnOriginal = libros.get(index).getIsbn();
        validarLibro(libro, true, isbnOriginal);
        libros.set(index, libro);
        // Mostrar el actualizado primero
        Libro actualizado = libros.remove(index);
        libros.add(0, actualizado);
        archivoLibro.guardarLibros(libros);
    }
    
    @Override
    public boolean eliminar(int index) {
        if (index < 0 || index >= libros.size()) {
            return false;
        }
        libros.remove(index);
        archivoLibro.guardarLibros(libros);
        return true;
    }
    //Metodos para Busquedas
    public Libro buscarPorISBN(String isbn) {
        for (Libro l : libros) {
            if (l.getIsbn().equalsIgnoreCase(isbn)) {
                return l;
            }
        }
        return null;
    }
    //Busqueda General
    public ArrayList<Libro> buscar(CriterioBusqueda criterio, String texto) {
        ArrayList<Libro> resultado = new ArrayList<>();
        for (Libro l : libros) {
            if (l.coincideCon(criterio, texto)) {
                resultado.add(l);
            }
        }
        return resultado;
    }
    //Validaciones para no romper los ficheros
    private void validarLibro(Libro libro,boolean esEdicion,String isbnOriginal) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede ser nulo.");
        }
        
        if (libro.getIsbn() == null || libro.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar un ISBN.");
        }

        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar un título.");
        }

        if (libro.getAutor() == null) {
            throw new IllegalArgumentException("Debe seleccionar un autor.");
        }

        if (libro.getCategoria() == null) {
            throw new IllegalArgumentException("Debe seleccionar una categoría.");
        }

        if (libro.getAnioPublicacion() <= 0) {
            throw new IllegalArgumentException("Ingrese un año válido.");
        }

        if (libro.getDescripcion() == null || libro.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar una descripción.");
        }

        if (libro.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }

        if (libro.getTipoLibro() == TipoLibro.FISICO) {
            if (libro.getUbicacionEstante() == null || libro.getUbicacionEstante().trim().isEmpty()) {
                throw new IllegalArgumentException("Debe ingresar la ubicación del estante.");
            }
        }

        if (libro.getTipoLibro() == TipoLibro.DIGITAL) {
            if (libro.getUrlAcceso() == null || libro.getUrlAcceso().trim().isEmpty()) {
                throw new IllegalArgumentException("Debe ingresar la URL del libro.");
            }
        }

        if (!esEdicion || !libro.getIsbn().equalsIgnoreCase(isbnOriginal)) {
            if (existeISBN(libro.getIsbn())) {
                throw new IllegalArgumentException("El ISBN ya está registrado.");
            }
        }
    }
    //Validar ISBN, nuestra base para saber que existe el libro realmente
    private boolean existeISBN(String isbn) {
        return buscarPorISBN(isbn) != null;
    }
    
    @Override
    public ArrayList<Libro> listar() {
        return new ArrayList<>(libros);
    }
}