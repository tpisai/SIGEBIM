/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.respaldo;

/**
 *
 * @author zulmi
 */
public class ArchivoRespaldo {
    private String nombre;
    private long tamanioBytes;
    private boolean disponible;

    public ArchivoRespaldo(String nombre, long tamanioBytes, boolean disponible) {
        this.nombre = nombre;
        this.tamanioBytes = tamanioBytes;
        this.disponible = disponible;
    }

    public String getNombre() {
        return nombre;
    }

    public long getTamanioBytes() {
        return tamanioBytes;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public String getTamanioFormateado() {
        if (tamanioBytes < 1024) {
            return tamanioBytes + " B";
        }
        return String.format("%.2f KB", tamanioBytes / 1024.0);
    }

    public String getEstado() {
        return disponible ? "Disponible" : "No encontrado";
    }
}