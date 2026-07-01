package modelo.reporte;

import java.time.LocalDate;
import modelo.interfaces.Reportable;
//Implements para obligar a que cada reporte tenga su contenido
public abstract class Reporte implements Reportable {
    private final String titulo;
    private final LocalDate fechaGeneracion;
    //Constructor
    public Reporte(String titulo) {
        this.titulo = titulo;
        this.fechaGeneracion = LocalDate.now();
    }
    //Titulo correcto para cada reporte
    public String getEncabezado() {
        return titulo + " - " + fechaGeneracion;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }
    
}
