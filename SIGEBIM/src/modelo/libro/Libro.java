package modelo.libro;

import modelo.enums.CriterioBusqueda;
import modelo.enums.EstadoLibro;
import modelo.enums.TipoLibro;
import modelo.interfaces.Prestable;

public class Libro extends MaterialBiblioteca implements Prestable {

    private int stock;
    private EstadoLibro estadoLibro;
    private String ubicacionEstante;
    private String urlAcceso;
    private TipoLibro tipoLibro;
    //Constructor
    public Libro() {
        this.estadoLibro = EstadoLibro.NO_DISPONIBLE;
        this.tipoLibro = TipoLibro.FISICO;
    }

    public Libro(String isbn, String titulo, Autor autor, Categoria categoria, int anioPublicacion, String descripcion,
                 int stock, String estado, TipoLibro tipoLibro, String ubicacionEstante, String urlAcceso) {
        super(isbn, titulo, autor, categoria, anioPublicacion, descripcion);
        this.stock = stock;
        this.tipoLibro = tipoLibro;
        this.ubicacionEstante = ubicacionEstante;
        this.urlAcceso = urlAcceso;
        this.estadoLibro = (estado == null || estado.isEmpty())
                ? EstadoLibro.desdeStock(stock)
                : EstadoLibro.desdeTexto(estado);
    }
    //Logica para si es fisico o virtual
    public boolean esFisico() {
        return tipoLibro == TipoLibro.FISICO;
    }

    public boolean esDigital() {
        return tipoLibro == TipoLibro.DIGITAL;
    }
    //Logica para Prestamos (Valido para Libro Fisico)
    @Override
    public boolean puedePrestarse() {
        return esFisico() && stock > 0 && estadoLibro == EstadoLibro.DISPONIBLE_PRESTAMO;
    }

    @Override
    public void registrarPrestamo() {
        if (!puedePrestarse()) {
            throw new IllegalStateException("El libro no está disponible para préstamo.");
        }
        stock--;
        recalcularEstado();
    }

    @Override
    public void registrarDevolucion() {
        stock++;
        recalcularEstado();
    }
    //Logica para Libro Digital
    public boolean tieneAccesoDigital() {
        return esDigital()
                && urlAcceso != null
                && !urlAcceso.trim().isEmpty();
    }
    //Logica para Libro fisico
    public boolean tieneStock() {
        return stock > 0;
    }
    
    public void recalcularEstado() {
        this.estadoLibro = EstadoLibro.desdeStock(stock);
    }
    //Para mantener la herencia
    @Override
    public boolean coincideCon(CriterioBusqueda criterio, String texto) {
        return super.coincideCon(criterio, texto);
    }
    //Getters y Setters
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
        recalcularEstado();
    }

    public int getAnioPublicacion() {
        return super.getAnioPublicacion();
    }

    public void setAnioPublicacion(int anioPublicacion) {
        super.setAnioPublicacion(anioPublicacion);
    }
    public TipoLibro getTipoLibro() {
        return tipoLibro;
    }

    public void setTipoLibro(TipoLibro tipoLibro) {
        this.tipoLibro = tipoLibro;
    }
    public String getUrlAcceso() {
        return urlAcceso;
    }

    public void setUrlAcceso(String urlAcceso) {
        this.urlAcceso = urlAcceso;
    }
        public String getUbicacionEstante() {
        return ubicacionEstante;
    }

    public void setUbicacionEstante(String ubicacionEstante) {
        this.ubicacionEstante = ubicacionEstante;
    }
    
    public EstadoLibro getEstadoLibro() {
        return estadoLibro;
    }
}