package modelo.persona;

import modelo.enums.RolUsuario;

public class Usuario extends Persona {
    private String username;
    private String password;
    private RolUsuario rol;
    //Constructor
    public Usuario() {
        super();
        rol = RolUsuario.LECTOR;
    }
    
    public Usuario(String codigo, String nombre, String dni, String correo, String username, String password, RolUsuario rol) {
        super(codigo, nombre, dni, correo);
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

     // Métodos para definir rol
    public boolean esAdministrador() {
        return rol == RolUsuario.ADMINISTRADOR;
    }

    public boolean esBibliotecario() {
        return rol == RolUsuario.BIBLIOTECARIO;
    }

    public boolean esLector() {
        return rol == RolUsuario.LECTOR;
    }
    
    @Override
    public String toString(){
        return getNombre() + " - " + rol.getTexto();
    }
    //Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

}
