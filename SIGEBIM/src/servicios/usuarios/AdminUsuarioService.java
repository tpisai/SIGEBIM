/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios.usuarios;

import archivos.ArchivoUsuario;
import java.util.ArrayList;
import modelo.enums.RolUsuario;
import modelo.persona.Usuario;

/**
 *
 * @author zulmi
 */
public class AdminUsuarioService {
    private final UsuarioService usuarioService;

    public AdminUsuarioService() {
        usuarioService = new UsuarioService();
    }
    
    public boolean activarUsuario(String dni){
        Usuario usuario = usuarioService.buscarPorDni(dni);
        if(usuario != null){
            usuario.setActivo(true);
            usuarioService.guardarCambios();
            return true;
        }
        return false;
    }
    
    public boolean desactivarUsuario(String Dni){
        Usuario usuario = usuarioService.buscarPorDni(Dni);
        if(usuario != null){
            usuario.setActivo(false);
            usuarioService.guardarCambios();
            return true;
        }
        return false;
    }
    
    public boolean cambiarPassword(String username,String nuevaPassword){
        Usuario usuario = usuarioService.buscarPorUsername(username);
        if(usuario==null){
            return false;
        }
        usuario.setPassword(nuevaPassword);
        usuarioService.guardarCambios();
        return true;
    }
    
    public boolean cambiarRol(String username,RolUsuario rol){
        Usuario usuario = usuarioService.buscarPorUsername(username);
        if(usuario==null){
            return false;
        }
        usuario.setRol(rol);
        usuarioService.guardarCambios();
        return true;
    }
}
