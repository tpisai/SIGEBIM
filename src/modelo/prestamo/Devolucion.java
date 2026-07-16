package modelo.prestamo;

import java.time.LocalDate;

public class Devolucion {
    private String codigo;
    private Prestamo prestamo;
    private LocalDate fechaRealDevolucion;
    private Multa multa;
    
    public Devolucion(){
    }
    
    //Metodo para colocar multa
    public boolean tieneRetraso() {
        return prestamo != null
                && fechaRealDevolucion != null
                && prestamo.getFechaLimite().isBefore(fechaRealDevolucion);
    }
    //Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public LocalDate getFechaRealDevolucion() {
        return fechaRealDevolucion;
    }

    public void setFechaRealDevolucion(LocalDate fechaRealDevolucion) {
        this.fechaRealDevolucion = fechaRealDevolucion;
    }

    public Multa getMulta() {
        return multa;
    }

    public void setMulta(Multa multa) {
        this.multa = multa;
    }
}
