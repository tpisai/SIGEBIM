package modelo.libro;

import modelo.enums.CriterioBusqueda;
import modelo.interfaces.Buscable;

public abstract class MaterialBiblioteca implements Buscable {
    private String isbn;
    private String titulo;
    private Autor autor;
    private Categoria categoria;
    private int anioPublicacion;
    private String descripcion;
    //Constructores
    public MaterialBiblioteca() {
    }

    public MaterialBiblioteca(String isbn, String titulo, Autor autor, Categoria categoria,
            int anioPublicacion, String descripcion) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.anioPublicacion = anioPublicacion;
        this.descripcion = descripcion;
    }
    //Busqueda Mejorada
    //Metodo para simplificar el codigo de coincideCon
    protected boolean contiene(String origen, String texto) {
        return origen != null && origen.toLowerCase().contains(texto);
    }
    //Me fascina como quedo yei
    public boolean coincideCon(CriterioBusqueda criterio, String texto) {
        String valor = texto == null ? "" : texto.toLowerCase();
        if (autor==null || categoria==null) return false;
        return switch (criterio) {
            case ISBN -> contiene(isbn,valor);
            case TITULO -> contiene(titulo, valor);
            case AUTOR -> contiene(autor.getNombre(), valor);
            case CATEGORIA -> contiene(categoria.getNombre(), valor);
            case GENERAL -> contiene(isbn, valor) || contiene(titulo, valor) 
                || contiene(autor.getNombre(), valor) || contiene(categoria.getNombre(), valor);
        };
    }
    
    @Override
    public String toString() {
        return titulo;
    }
    //Getters y Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
