package servicios.usuarios;
//@author zulmi
import java.util.ArrayList;
import modelo.enums.RolUsuario;
import modelo.interfaces.Gestionable;
import modelo.persona.Usuario;

public class AdminUsuarioService implements Gestionable<Usuario>{

    private final UsuarioService usuarioService;

    public AdminUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    //CRUD
    @Override
    public void registrar(Usuario usuario) {
        usuarioService.registrar(usuario);
    }
    @Override
    public void actualizar(int index, Usuario usuario) {
        usuarioService.actualizar(index, usuario);
    }
    @Override
    public boolean eliminar(int index) {
        return usuarioService.eliminar(index);
    }
    //Funciones unicas de administrador
    public boolean cambiarEstado(String dni, boolean activo) {
        Usuario usuario = usuarioService.buscarPorDni(dni);
        if (usuario == null) {
            return false;
        }
        usuario.setActivo(activo);
        usuarioService.guardarCambios();
        return true;
    }

    public boolean cambiarRol(String username, RolUsuario rol) {
        Usuario usuario = usuarioService.buscarPorUsername(username);
        if (usuario == null) {
            return false;
        }
        usuario.setRol(rol);
        usuarioService.guardarCambios();
        return true;
    }

    public boolean cambiarPassword(String username, String nuevaPassword) {
        if (nuevaPassword == null || nuevaPassword.isBlank()) {
            throw new IllegalArgumentException("Ingrese una contraseña.");
        }
        Usuario usuario = usuarioService.buscarPorUsername(username);
        if (usuario == null) {
            return false;
        }
        usuario.setPassword(nuevaPassword);
        usuarioService.guardarCambios();
        return true;
    }
    //Consultas para el form
    @Override
    public ArrayList<Usuario> listar() {
        return usuarioService.listar();
    }

    public Usuario buscarPorDni(String dni) {
        return usuarioService.buscarPorDni(dni);
    }

    public Usuario buscarPorUsername(String username) {
        return usuarioService.buscarPorUsername(username);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarioService.buscarPorCorreo(correo);
    }
}