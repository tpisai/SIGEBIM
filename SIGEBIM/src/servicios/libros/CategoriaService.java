package servicios.libros;

import java.util.ArrayList;
import modelo.libro.Categoria;
import archivos.ArchivoCategoria;
import archivos.ArchivoLibro;
import modelo.libro.Libro;
import modelo.interfaces.Gestionable;

public class CategoriaService implements Gestionable<Categoria>{
    private final ArrayList<Categoria> categorias;
    private final ArchivoCategoria archivoCategoria;
    private final ArchivoLibro archivoLibro;
    // Constructor
    public CategoriaService() {
        archivoCategoria = new ArchivoCategoria();
        archivoLibro = new ArchivoLibro();
        categorias = archivoCategoria.cargarCategorias();
    }
    // Registrar nueva categoría
    public void registrar(Categoria categoria) {
        String id = categoria.getId(), nombre = categoria.getNombre();
        validarCategorias(id, nombre, false, null, null);
        categorias.add(0, new Categoria(id, nombre));
        archivoCategoria.guardarCategorias(categorias);
    }

    // Editar categoría existente y a lavez actualizara el libros.txt
    public void actualizar(int index, Categoria categoria) {
        String id = categoria.getId(), nombre = categoria.getNombre();
        if (index >= 0 && index < categorias.size()) {
            Categoria c = categorias.get(index);
            String idOriginal = c.getId(), nombreOriginal= c.getNombre();
            validarCategorias(id, nombre, true, idOriginal, nombreOriginal);
            c.setId(id);
            c.setNombre(nombre);
            archivoCategoria.guardarCategorias(categorias);
            ActualizarLibros(idOriginal, id, nombre);
        }
    }

    // Eliminar categoría con validación de libros asociados
    public boolean eliminar(int index) {
        if (index >= 0 && index < categorias.size()) {
            Categoria categoria = categorias.get(index);
            // Validar si hay libros asociados a esta categoría
            ArrayList<Libro> libros = archivoLibro.cargarLibros();
            for (Libro l : libros) {
                if (l.getCategoria().getId().equalsIgnoreCase(categoria.getId())) {
                    return false;
                }
            }
            categorias.remove(index);
            archivoCategoria.guardarCategorias(categorias);
            return true;
        }
        return false;
    }

    //Validaciones (Si, me gusta que todo tenga un orden impermutable) ;)
    private void validarFormatoId(String id) {
        if (!id.matches("^C-\\d+$")) {
            throw new IllegalArgumentException("El ID debe tener el formato C-# (recomendable: C-01, C-25).");
        }
    }

    //Validaciones por ID y Nombre
    private void validarCategorias(String id, String nombre, boolean esEdicion, String idOriginal, String nombreOriginal) {
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
    //busquedas
    public Categoria buscarPorNombre(String nombre){
        for (Categoria c : categorias){
            if (c.getNombre().trim().equalsIgnoreCase(nombre)){
                return c;
            }
        }
        return null;
    }
   
    public Categoria buscarPorId(String id) {
        for (Categoria c : categorias) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }
    //metodos para validacion
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
                if (l.getCategoria().getId().equalsIgnoreCase(idOriginal)) {
                    l.getCategoria().setId(id);
                    l.getCategoria().setNombre(nombre);
                }
            }
            archivoLibro.guardarLibros(libros);
    }
    // Para facilitar listado
    public ArrayList<Categoria> listar() {
        return new ArrayList<Categoria>(categorias);
    }
}
