package servicios.libros;
//@author zulmi
import archivos.ArchivoAutor;
import archivos.ArchivoLibro;
import java.util.ArrayList;
import modelo.libro.Autor;
import modelo.libro.Libro;
import modelo.interfaces.Gestionable;

public class AutorService implements Gestionable<Autor>{
    private final ArrayList<Autor> autores;
    private final ArchivoAutor archivoAutor;
    private final ArchivoLibro archivoLibro;

    public AutorService() {
        archivoAutor = new ArchivoAutor();
        archivoLibro = new ArchivoLibro();
        autores = archivoAutor.cargarAutores();
    }

    // Agregar, ahora si permite que el ultimo se vea desde el principio
    public void registrar(Autor autor) {
        String id = autor.getId(), nombre = autor.getNombre();
        validarAutor(id, nombre, false, null, null);
        autores.add(0, new Autor(id,nombre));
        archivoAutor.guardarAutores(autores);
    }

    // Actualizar algun autor
    public void actualizar(int index, Autor autor) {
        String id = autor.getId(), nombre = autor.getNombre();
        if (index >= 0 && index < autores.size()) {
            Autor a = autores.get(index);
            String idOriginal = a.getId(), nombreOriginal = a.getNombre();
            validarAutor(id, nombre, true, idOriginal, nombreOriginal);
            a.setId(id);
            a.setNombre(nombre);
            archivoAutor.guardarAutores(autores);
            ActualizarLibros(idOriginal, id, nombre);
        }
    }

    // condicional, elige si eliminar dependiendo si no hay libro asociado
    public boolean eliminar(int index) {
        if (index >= 0 && index < autores.size()) {
            Autor autor = autores.get(index);
            // Verificar si hay libros asociados
            ArrayList<Libro> libros = archivoLibro.cargarLibros();
            for (Libro l : libros) {
                if (l.getAutor().getId().equals(autor.getId())) {
                    return false;
                }
            }
            // Si no tiene libros asociados → eliminar
            autores.remove(index);
            archivoAutor.guardarAutores(autores);
            return true;
        }
        return false;
    }
    //Validaciones (Si, me gusta que todo tenga un orden impermutable) ;)
    private void validarFormatoId(String id) {
        if (!id.matches("^A-\\d+$")) {
            throw new IllegalArgumentException("El ID debe tener el formato A-# (recomendable: A-001, A-025).");
        }
    }

    //Validaciones por ID y Nombre
    private void validarAutor(String id, String nombre, boolean esEdicion, String idOriginal, String nombreOriginal) {
        if (id == null || nombre == null) {
            throw new IllegalArgumentException("Debe rellenar todos los campos.");
        }
        validarFormatoId(id);
        // Si es edición, permitimos que el ID/nombre sean iguales al original
        if (!esEdicion || !id.equalsIgnoreCase(idOriginal)) {
            if (existeId(id)) {
                throw new IllegalArgumentException("ID duplicado, proceso cancelado.");
            }
        }
        if (!esEdicion || !nombre.equalsIgnoreCase(nombreOriginal)) {
            if (existeNombre(nombre)) {
                throw new IllegalArgumentException("Nombre duplicado, proceso cancelado.");
            }
        }
    }
    //Busquedas
    public Autor buscarPorNombre(String nombre){
        for (Autor a : autores){
            if (a.getNombre().trim().equalsIgnoreCase(nombre)){
                return a;
            }
        }
        return null;
    }
   
    public Autor buscarPorId(String id) {
        for (Autor a : autores) {
            if (a.getId().equalsIgnoreCase(id)) {
                return a;
            }
        }
        return null;
    }
    //Para validacion
    private boolean existeId(String id) {
        return buscarPorId(id) != null;
    }
    
    private boolean existeNombre(String nombre){
        return buscarPorNombre(nombre) != null;
    }
    //para que las actualizaciones tengan sentido en todo el sistema
    private void ActualizarLibros(String idOriginal, String id, String nombre){
        ArrayList<Libro> libros = archivoLibro.cargarLibros();
            for (Libro l : libros) {
                if (l.getAutor().getId().equalsIgnoreCase(idOriginal)) {
                    l.getAutor().setId(id);
                    l.getAutor().setNombre(nombre);
                }
            }
            archivoLibro.guardarLibros(libros);
    }
    // Para simplificar
    public ArrayList<Autor> listar() {
        return new ArrayList<Autor>(autores);
    }
}