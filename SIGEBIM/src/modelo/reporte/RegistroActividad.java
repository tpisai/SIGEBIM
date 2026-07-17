/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.reporte;

import java.time.LocalDateTime;

/**
 *
 * @author zulmi
 */
public class RegistroActividad {

    private String codigo;
    private LocalDateTime fechaHora;
    private String usuario;
    private String rol;
    private String modulo;
    private String accion;
    private String detalle;
    private String resultado;

    public RegistroActividad(String codigo, LocalDateTime fechaHora, String usuario, String rol, String modulo,
            String accion, String detalle, String resultado) {
        this.codigo = codigo;
        this.fechaHora = fechaHora;
        this.usuario = usuario;
        this.rol = rol;
        this.modulo = modulo;
        this.accion = accion;
        this.detalle = detalle;
        this.resultado = resultado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}