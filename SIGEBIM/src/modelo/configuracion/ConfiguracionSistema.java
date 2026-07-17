package modelo.configuracion;

public class ConfiguracionSistema {
    private String carpetaRespaldo;
    private int diasMaximoPrestamo;
    private double multaDiaria;
    private int maximoPersonasCola;
    private double costoReposicion;
    //Constructor
    public ConfiguracionSistema() {
        this.carpetaRespaldo = "C:\\RespaldoSIGEBIM";
        this.diasMaximoPrestamo = 3;
        this.multaDiaria = 2.50;
        this.maximoPersonasCola = 5;
        this.costoReposicion = 50.00;
    }

    public ConfiguracionSistema(String carpetaRespaldo, int diasMaximoPrestamo, double multaDiaria, int maximoPersonasCola, double costoReposicion) {
        this.carpetaRespaldo = carpetaRespaldo;
        this.diasMaximoPrestamo = diasMaximoPrestamo;
        this.multaDiaria = multaDiaria;
        this.maximoPersonasCola = maximoPersonasCola;
        this.costoReposicion = costoReposicion;
    }

    public String getCarpetaRespaldo() {
        return carpetaRespaldo;
    }

    public void setCarpetaRespaldo(String carpetaRespaldo) {
        this.carpetaRespaldo = carpetaRespaldo;
    }

    public int getDiasMaximoPrestamo() {
        return diasMaximoPrestamo;
    }

    public void setDiasMaximoPrestamo(int diasMaximoPrestamo) {
        this.diasMaximoPrestamo = diasMaximoPrestamo;
    }

    public double getMultaDiaria() {
        return multaDiaria;
    }

    public void setMultaDiaria(double multaDiaria) {
        this.multaDiaria = multaDiaria;
    }

    public int getMaximoPersonasCola() {
        return maximoPersonasCola;
    }

    public void setMaximoPersonasCola(int maximoPersonasCola) {
        this.maximoPersonasCola = maximoPersonasCola;
    }

    public double getCostoReposicion() {
        return costoReposicion;
    }

    public void setCostoReposicion(double costoReposicion) {
        this.costoReposicion = costoReposicion;
    }
    
}