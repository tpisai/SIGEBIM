package modelo.prestamo;

public class Multa {
    private String codigo;
    private Prestamo prestamo;
    private double monto;
    private boolean pagada;
    //Constructor
    public Multa() {
    }

    public Multa(String codigo, Prestamo prestamo, double monto) {
        this.codigo = codigo;
        this.prestamo = prestamo;
        this.monto = monto;
        this.pagada = false;
    }
    //Metodo
    public void pagar() {
        this.pagada = true;
    }

    public boolean estaPendiente() {
        return !pagada && monto > 0;
    }
    public double calcularDiasRetraso() {
        if (prestamo == null || prestamo.getFechaLimite() == null) return 0;

        return java.time.temporal.ChronoUnit.DAYS.between(
                prestamo.getFechaLimite(),
                java.time.LocalDate.now()
        );
    }
    //Getter y Setter
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

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public boolean isPagada() {
        return pagada;
    }

    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }
}
